#
# This file was derived from the 'Hello World!' example recipe in the
# Yocto Project Development Manual.
#

DESCRIPTION = "Control application for ClusterHAT"
SECTION = "clusterctrl"
DEPENDS = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=bb6e931b02e57931863cefb015ae046c"

SRCREV = "96ad98ec8f42e96557e3ea86ae5a57a0a4f6cbac"
SRC_URI = "git://github.com/burtyb/clusterhat-image.git;protocol=https;branch=master \
           file://0001-Changed-xclusterhat-to-use-python3.patch \
           file://0001-Changed-import-of-glob-to-glob2.patch \
           file://85-wireless.network \
           "

S = "${WORKDIR}/git"

def get_files(d):
    if bb.utils.to_boolean(d.getVar('WIFI_CONFIG'), False):
        return " ".join([
            "${sysconfdir}/systemd/system/multi-user.target.wants/wpa_supplicant@wlan0.service",
            "${sysconfdir}/wpa_supplicant/wpa_supplicant-wlan0.conf",
            "${systemd_unitdir}/network/85-wireless.network"
        ])
    return ""

FILES:${PN} = " \
    /usr/** \
    ${systemd_system_unitdir}/clusterctrl-init.service \
    ${@get_files(d)} \
    "

WIFI_CONFIG ?= "n"
WIFI_SSID ?= ""
WIFI_PASSWORD ?= ""

do_compile() {
}

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}/${sbindir}
    install -d ${D}/${datadir}

	install -m 644 ${S}/files/usr/lib/systemd/system/clusterctrl-init.service ${D}/${systemd_system_unitdir}
    cp -r --no-dereference --preserve=mode,links -v ${S}/files/usr/sbin/* ${D}/${sbindir}
    cp -r --no-dereference --preserve=mode,links -v ${S}/files/usr/share/* ${D}/${datadir}

    if [ "${@ 'on' if bb.utils.to_boolean(d.getVar('WIFI_CONFIG'), False) else ''}" = "on" ]; then
        install -d ${D}${sysconfdir}/wpa_supplicant
        install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
        install -d ${D}${systemd_unitdir}/network

        cat > ${D}${sysconfdir}/wpa_supplicant/wpa_supplicant-wlan0.conf << EOF
ctrl_interface=/var/run/wpa_supplicant
ctrl_interface_group=0
update_config=1

network={
        ssid="${WIFI_SSID}"
        psk="${WIFI_PASSWORD}"
        proto=RSN
        key_mgmt=WPA-PSK
 }
EOF

        ln -sf ${systemd_system_unitdir}/wpa_supplicant@.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/wpa_supplicant@wlan0.service
        install -m 644 ${WORKDIR}/85-wireless.network ${D}${systemd_unitdir}/network
    fi
}

RDEPENDS:${PN} = " bash python3-core python3-smbus rpi-gpio python3-glob2 python3-pyusb python3-libusb1"

inherit systemd

SYSTEMD_SERVICE:${PN} = "clusterctrl-init.service"
