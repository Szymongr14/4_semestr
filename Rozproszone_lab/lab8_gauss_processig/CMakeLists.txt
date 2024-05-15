cmake_minimum_required(VERSION 3.28)
project(lab8_gauss_processig)

set(CMAKE_CXX_STANDARD 17)

find_package(PNG REQUIRED)

add_executable(lab8_gauss_processig main.cpp)

target_link_libraries(lab8_gauss_processig PNG::PNG)
