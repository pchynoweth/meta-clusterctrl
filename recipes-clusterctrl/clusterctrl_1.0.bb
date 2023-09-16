#
# This file was derived from the 'Hello World!' example recipe in the
# Yocto Project Development Manual.
#

DESCRIPTION = "Control application for ClusterHAT"
SECTION = "clusterctrl"
DEPENDS = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=bb6e931b02e57931863cefb015ae046c"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${PV}:"

SRCREV = "96ad98ec8f42e96557e3ea86ae5a57a0a4f6cbac"
SRC_URI = "git://github.com/burtyb/clusterhat-image.git;protocol=https;branch=master \
           file://0001-Changed-xclusterhat-to-use-python3.patch \
           file://0001-Changed-import-of-glob-to-glob2.patch \
           "

S = "${WORKDIR}/git"

FILES:${PN} = "/usr/** ${systemd_system_unitdir}/clusterctrl-init.service"

do_compile() {
}

do_install() {
    #install -d ${D}/${sbindir}
    #install -m 0755 ${S}/files/usr/sbin/* ${D}/${sbindir}

    #install -d ${D}/${datadir}/clusterctrl/
    #install -m 0755 ${S}/files/usr/share/clusterctrl/* ${D}/${datadir}/clusterctrl/
    
    #install -d ${D}/${datadir}/clusterctrl/python/
    #install -m 0755 ${S}/files/usr/share/clusterctrl/python/* ${D}/${datadir}/clusterctrl/python/

    install -d ${D}${systemd_system_unitdir}
    install -d ${D}/${sbindir}
    install -d ${D}/${datadir}

	install -m 644 ${S}/files/usr/lib/systemd/system/clusterctrl-init.service ${D}/${systemd_system_unitdir}
    cp -r --no-dereference --preserve=mode,links -v ${S}/files/usr/sbin/* ${D}/${sbindir}
    cp -r --no-dereference --preserve=mode,links -v ${S}/files/usr/share/* ${D}/${datadir}
}

RDEPENDS:${PN} = " bash python3-core python3-smbus rpi-gpio python3-glob2 python3-pyusb python3-libusb1"

inherit systemd

SYSTEMD_SERVICE:${PN}:append = "clusterctrl-init.service"
