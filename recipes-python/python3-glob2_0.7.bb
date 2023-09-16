
SUMMARY = "Version of the glob module that can capture patterns and supports recursive wildcards"
HOMEPAGE = "http://github.com/miracle2k/python-glob2/"
AUTHOR = "Michael Elsdoerfer <michael@elsdoerfer.com>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=01c7e9175fd063ebb0a6304af80e9874"

SRC_URI = "https://files.pythonhosted.org/packages/d7/a5/bbbc3b74a94fbdbd7915e7ad030f16539bfdc1362f7e9003b594f0537950/glob2-0.7.tar.gz"
SRC_URI[md5sum] = "762be5ff1a29c0c3a1564e949e5d1228"
SRC_URI[sha256sum] = "85c3dbd07c8aa26d63d7aacee34fa86e9a91a3873bc30bf62ec46e531f92ab8c"

S = "${WORKDIR}/glob2-0.7"

RDEPENDS:${PN} = ""

inherit setuptools3
