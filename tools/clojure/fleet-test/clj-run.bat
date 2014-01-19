set REPO=D:\repository

set CLASS_PATH=.\*
set CLASS_PATH=.\src;%CLASS_PATH%


java -cp %CLASS_PATH% clojure.main %*
