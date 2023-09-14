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

# Use patched version until figure out how to handle QA error missing python (due to python3 installed and script uses /usr/bin/python)
#SRC_URI = "git://github.com/burtyb/clusterhat-image.git;protocol=https;branch=master"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/ihatetoregister/clusterhat-image.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_compile() {
}

do_install() {
    #install -d ${D}/${sbindir}
    #install -m 0755 ${S}/files/usr/sbin/* ${D}/${sbindir}

    #install -d ${D}/${datadir}/clusterctrl/
    #install -m 0755 ${S}/files/usr/share/clusterctrl/* ${D}/${datadir}/clusterctrl/
    
    #install -d ${D}/${datadir}/clusterctrl/python/
    #install -m 0755 ${S}/files/usr/share/clusterctrl/python/* ${D}/${datadir}/clusterctrl/python/

    install -d ${D}/${sbindir}
    install -d ${D}/${datadir}
    cp -r --no-dereference --preserve=mode,links -v ${S}/files/usr/sbin/* ${D}/${sbindir}
    cp -r --no-dereference --preserve=mode,links -v ${S}/files/usr/share/* ${D}/${datadir}
}

RDEPENDS:${PN} = " bash python3-core python3-smbus rpi-gpio"
