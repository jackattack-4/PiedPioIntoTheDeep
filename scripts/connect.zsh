# A quick zsh script that connects to the control hub. You need ADB installed and to be on the RC's wifi (11201-RC-A/B).

adb kill-server
adb start-server
adb tcpip 5555
adb connect 192.168.43.1:5555