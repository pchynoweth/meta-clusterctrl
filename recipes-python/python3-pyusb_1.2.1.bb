
SUMMARY = "Python USB access module"
HOMEPAGE = "https://pyusb.github.io/pyusb"
AUTHOR = "Jonas Malaco <me@jonasmalaco.com>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e64a29fcd3c3dd356a24e235dfcb3905"

SRC_URI = "https://files.pythonhosted.org/packages/d9/6e/433a5614132576289b8643fe598dd5d51b16e130fd591564be952e15bb45/pyusb-1.2.1.tar.gz"
SRC_URI[md5sum] = "880008dff32dac8f58076b4e534492d9"
SRC_URI[sha256sum] = "a4cc7404a203144754164b8b40994e2849fde1cfff06b08492f12fff9d9de7b9"

S = "${WORKDIR}/pyusb-1.2.1"

DEPENDS = "python3-pip"

RDEPENDS_${PN} = ""

inherit setuptools3
