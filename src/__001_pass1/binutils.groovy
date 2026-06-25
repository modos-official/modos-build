

sh """

   cd $LFS/sources
   tar -xf binutils-2.40.tar.xz
   cd binutils-2.40
   mkdir -v build
   cd build


   ../configure --prefix=$LFS/tools \
             --with-sysroot=$LFS \
             --target=$LFS_TGT   \
             --disable-nls       \
             --enable-gprofng=no \
             --disable-werror    \
             --enable-new-dtags  \
             --enable-default-hash-style=gnu

    make 
    
    make install
"""


// gcc pass 1

sh """


    cd $LFS/sources
    tar -xf gcc-13.2.0.tar.xz
    cd gcc-13.2.0


    tar -xf ../mpfr-4.2.2.tar.xz
    mv -v mpfr-4.2.2 mpfr
    tar -xf ../gmp-6.3.0.tar.xz
    mv -v gmp-6.3.0 gmp
    tar -xf ../mpc-1.3.1.tar.gz 
    mv -v mpc-1.3.1 mpc



    mkdir -v build
    cd build

    ../configure --target=$LFS_TGT \
                --prefix=$LFS/tools \
                --with-glibc-version=2.36 \
                --with-sysroot=$LFS \
                --with-newlib \
                --without-headers \
                --enable-initfini-array \
                --disable-nls \
                --disable-shared \
                --disable-multilib \
                --disable-decimal-float \
                --disable-threads \
                --disable-libatomic \
                --disable-libgomp \
                --disable-libquadmath \
                --disable-libssp \
                --disable-libvtv \
                --disable-libstdcxx

    make

    make install
"""