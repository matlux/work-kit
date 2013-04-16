{
        printf($2 " ");
        for(i=1; i<NF; ++i) {
                if($i ~ /.*member=.*/) {
                        split($i, tab, "=");
                        print tab[2];
                }
        }
}

