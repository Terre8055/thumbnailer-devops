#!/bin/bash

CP="thumbnailer.jar:lib/*"

java -cp "${CP}" com.develeap.thumbnailer.Main $@
