#!/bin/bash

while /bin/true; do
	echo "committing!";
	git add .;
	git commit -a --allow-empty -m "commit @ `date` `cat doing`";
	git push;
	echo "done!";
	sleep 15m;
done
