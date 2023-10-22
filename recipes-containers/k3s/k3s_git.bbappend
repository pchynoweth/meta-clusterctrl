FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

RPI_ZERO_PATCHES = "file://0001-Changed-pause-image-to-an-armv6-compatible-image.patch;patchdir=src/import"
SRC_URI:append:raspberrypi0 = " ${RPI_ZERO_PATCHES}"
SRC_URI:append:raspberrypi0-wifi = " ${RPI_ZERO_PATCHES}"

