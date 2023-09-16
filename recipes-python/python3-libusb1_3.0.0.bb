
SUMMARY = "Pure-python wrapper for libusb-1.0"
HOMEPAGE = "https://github.com/vpelletier/python-libusb1"
AUTHOR = "Vincent Pelletier <plr.vincent@gmail.com>"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING.LESSER;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "https://files.pythonhosted.org/packages/f4/83/59bf75e74e0c4859ea63eae0c7da660c1dcb78b31667d4a5f735d52f5974/libusb1-3.0.0.tar.gz"
SRC_URI[md5sum] = "ffbb02bf9aa49f973a6a58112aed7b06"
SRC_URI[sha256sum] = "5792a9defee40f15d330a40d9b1800545c32e47ba7fc66b6f28f133c9fcc8538"

S = "${WORKDIR}/libusb1-3.0.0"

RDEPENDS_${PN} = ""

inherit setuptools3
