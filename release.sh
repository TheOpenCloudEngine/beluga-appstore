#!/bin/bash

mvn clean

mvn release:prepare || exit

mvn release:clean