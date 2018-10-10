
PV = "1.8.0"
PV_UPDATE = "181"
BUILD_NUMBER = "8.31.1.122"
SUFFIX = "linux_aarch32hf"

SUMMARY = "Azul Zulu Java Development Kit (JDK) binaries"
DESCRIPTION = "This the Embedded JDK for the 32 bit ARM architecture from \
 Azul Systems Inc. It is created using OpenJDK code, which is licensed under \
 GPLv2 and various other third party licenses. Azul offers a variety of \
 support plans, as well as patent indemnification and guarantees against \
 the risk of open source viral contamination, as part of its Zulu \
 Embedded commercial offerings."

BBCLASSEXTEND = "native"

LICENSE = "GPL-2.0-with-classpath-exception"
LIC_FILES_CHKSUM = "file://zulu${BUILD_NUMBER}-jdk${PV}_${PV_UPDATE}-${SUFFIX}/LICENSE;md5=3e0b59f8fac05c3c03d4a26bbda13f8f"

SRC_URI="http://cdn.azul.com/zulu-embedded/bin/zulu${BUILD_NUMBER}-jdk${PV}_${PV_UPDATE}-${SUFFIX}.tar.gz"

SRC_URI[md5sum] = "a07cf2a3b8d4ff75d609636bbdfc0078"
SRC_URI[sha256sum] = "fe014c3b8226d99521f0b1447f0337f27ea7689b653ee5015e4838063633be7c"

PR = "u${PV_UPDATE}"
S = "${WORKDIR}"

python do_install () {
    bb.build.exec_func("shell_do_install", d)
    oe.path.make_relative_symlink(d.expand("${D}${bindir}/java"))
}

shell_do_install() {
  install -d -m 0755 ${D}${datadir}/zulu${BUILD_NUMBER}-jdk${PV}_${PV_UPDATE}-${SUFFIX}
  cp -a ${S}/zulu${BUILD_NUMBER}-jdk${PV}_${PV_UPDATE}-${SUFFIX}/* ${D}${datadir}/zulu${BUILD_NUMBER}-jdk${PV}_${PV_UPDATE}-${SUFFIX}
  install -d -m 0755 ${D}${bindir}
  ln -sf ${datadir}/zulu${BUILD_NUMBER}-jdk${PV}_${PV_UPDATE}-${SUFFIX}/bin/java ${D}${bindir}/java
}

# All the files are provided in a binaray package, and keeping all the
# files in a single package causes packaging QA errors and warnings.
# Avoid these packaging failure by skiping all the QA checks
INSANE_SKIP_${PN} = "${ERROR_QA} ${WARN_QA}"

FILES_${PN} = "/usr/"
RPROVIDES_${PN} = "zulu-jdk"
PROVIDES += "virtual/java"

DEPENDS = "alsa-lib libxi libxrender libxtst"
