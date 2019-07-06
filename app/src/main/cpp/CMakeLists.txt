
cmake_minimum_required(VERSION 3.4.1)

file(GLOB bzip_source ${CMAKE_SOURCE_DIR}/bzip/*.c)

add_library(
        native-lib


        SHARED

        bspatch.c
        ${bzip_source}
        native-lib.cpp)


find_library(
        log-lib


        log)

target_link_libraries(
        native-lib

        ${log-lib})