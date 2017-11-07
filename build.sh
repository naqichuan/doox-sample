#!/bin/bash
mvn clean
mvn -U package -Dmaven.test.skip=true
