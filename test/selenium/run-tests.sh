printf "\n"
echo "-----------------------------------------------------"
IFS= read -s -p Username: usr
printf "\n"
IFS= read -s -p Password: pwd
printf "\n"
echo "-----------------------------------------------------"
printf "\n\n"
    
python template.py $pwd $usr
