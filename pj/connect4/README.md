4-i-rad på VG-nivå. Körs enklast med `gradle run`. Annars kan den också köras manuellt med `App::main`. 

git-genvägar:
```bash
# https
git clone https://github.com/emanueljg/jensenyh.git
# ssh
git clone git@github.com:emanueljg/jensenyh.git
# Github CLI
gh repo clone emanueljg/jensenyh
```

unix-genväg för att "packa upp" connect4 och inget annat:
```bash
# anta att repot finns i working dir
cp -r jensenyh/pj/connect4 . && rm -r jensenyh
```
