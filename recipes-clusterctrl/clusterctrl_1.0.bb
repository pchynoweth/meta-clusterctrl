#
# This file was derived from the 'Hello World!' example recipe in the
# Yocto Project Development Manual.
#

DESCRIPTION = "Control application for ClusterHAT"
SECTION = "clusterctrl"
DEPENDS = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7ae2be7fb1637141840314b51970a9f7"

SRCREV = "96ad98ec8f42e96557e3ea86ae5a57a0a4f6cbac"
SRC_URI = "git://github.com/burtyb/clusterhat-image.git;protocol=https;branch=master \
           file://0001-Changed-xclusterhat-to-use-python3.patch \
           file://0001-Changed-import-of-glob-to-glob2.patch \
           file://60-br0.netdev \
           file://60-brint.netdev \
           file://65-br0.network \
           file://65-brint.network \
           "

S = "${WORKDIR}/git"

FILES:${PN} = " \
    /usr/** \
    ${systemd_system_unitdir}/clusterctrl-init.service \
    ${sysconfdir}/udev/rules.d/90-clusterctrl.rules \
    ${sysconfdir}/minicom/minirc.* \
    ${sysconfdir}/kernel/postinst.d/clusterctrl \
    ${sysconfdir}/iptables/rules.v4 \
    ${sysconfdir}/dhcpcd.conf \
    ${systemd_unitdir}/network/* \
    "

WIFI_CONFIG ?= "n"
WIFI_SSID ?= ""
WIFI_PASSWORD ?= ""

do_compile() {
}

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}${sbindir}
    install -d ${D}${datadir}
    install -d ${D}${sysconfdir}/udev/rules.d
    install -d ${D}${sysconfdir}/minicom
    install -d ${D}${sysconfdir}/kernel/postinst.d
    install -d ${D}${sysconfdir}/iptables
    install -d ${D}${systemd_unitdir}/network

	install -m 644 ${S}/files/usr/lib/systemd/system/clusterctrl-init.service ${D}/${systemd_system_unitdir}
	install -m 644 ${S}/files/usr/lib/systemd/system/clusterctrl-composite.service ${D}/${systemd_system_unitdir}
    cp -r --no-dereference --preserve=mode,links -v ${S}/files/usr/sbin/* ${D}/${sbindir}
    cp -r --no-dereference --preserve=mode,links -v ${S}/files/usr/share/* ${D}/${datadir}

    install -m 644 ${S}/files/etc/udev/rules.d/90-clusterctrl.rules ${D}${sysconfdir}/udev/rules.d

    install -m 644 ${S}/files/etc/minicom/minirc.* ${D}${sysconfdir}/minicom

    install -m 644 ${S}/files/etc/kernel/postinst.d/clusterctrl ${D}${sysconfdir}/kernel/postinst.d

    cat << EOF >> ${D}${sysconfdir}/iptables/rules.v4
# Generated by iptables-save v1.6.0 on Fri Mar 13 00:00:00 2018
*filter
:INPUT ACCEPT [7:1365]
:FORWARD ACCEPT [0:0]
:OUTPUT ACCEPT [0:0]
-A FORWARD -i br0 ! -o br0 -j ACCEPT
-A FORWARD -o br0 -m conntrack --ctstate RELATED,ESTABLISHED -j ACCEPT
COMMIT
# Completed on Fri Mar 13 00:00:00 2018
# Generated by iptables-save v1.6.0 on Fri Mar 13 00:00:00 2018
*nat
:PREROUTING ACCEPT [8:1421]
:INPUT ACCEPT [7:1226]
:OUTPUT ACCEPT [0:0]
:POSTROUTING ACCEPT [0:0]
-A POSTROUTING -s 172.19.181.0/24 ! -o br0 -j MASQUERADE
COMMIT
# Completed on Fri Mar 13 00:00:00 2018
EOF

    cat << EOF >> ${D}${sysconfdir}/dhcpcd.conf
# ClusterCTRL
reboot 15
denyinterfaces ethpi* ethupi* ethupi*.10 brint eth0 usb0.10

profile clusterctrl_fallback_usb0
static ip_address=172.19.181.253/24 #ClusterCTRL
static routers=172.19.181.254
static domain_name_servers=8.8.8.8 208.67.222.222

profile clusterctrl_fallback_br0
static ip_address=172.19.181.254/24

interface usb0
fallback clusterctrl_fallback_usb0

interface br0
fallback clusterctrl_fallback_br0
EOF

    install -m 644 ${WORKDIR}/60-br0.netdev ${WORKDIR}/65-br0.network ${D}${systemd_unitdir}/network
    install -m 644 ${WORKDIR}/60-brint.netdev ${WORKDIR}/65-brint.network ${D}${systemd_unitdir}/network

    if [ ${CLUSTERCTRL_VARIANT} = "cbridge" ]; then
        for i in 1 2 3 4 5; do
            cat > ${D}${systemd_unitdir}/network/65-ethupi$i.10 << EOF
[Match]
Name=ethupi$i.10

[Network]
Bridge=brint
EOF
        done
    fi

    if [ ${CLUSTERCTRL_VARIANT} = "cbridge" ] || [ ${CLUSTERCTRL_VARIANT} = "cnat" ]; then
        for i in 1 2 3 4 5; do
            cat > ${D}${systemd_unitdir}/network/65-ethpi$i << EOF
[Match]
Name=ethpi$i

[Network]
Bridge=br0
EOF
        done

        for i in 1 2 3 4 5; do
            cat > ${D}${systemd_unitdir}/network/65-ethupi$i << EOF
[Match]
Name=ethupi$i

[Network]
Bridge=br0
EOF
        done
    fi
}

RDEPENDS:${PN} = " bash bridge-utils dhcpcd minicom nfs-utils python3-core python3-smbus rpi-gpio python3-glob2 python3-pyusb python3-libusb1"

inherit systemd features_check

SYSTEMD_SERVICE:${PN} = "clusterctrl-init.service clusterctrl-composite.service"
REQUIRED_DISTRO_FEATURES = "systemd"