#!/bin/bash
java -jar normalizer/target/normalizer-0.0.1-SNAPSHOT.jar &
cd normalizer-ui
ng serve --proxy-config proxy.conf.json --host 0.0.0.0 --disable-host-check
