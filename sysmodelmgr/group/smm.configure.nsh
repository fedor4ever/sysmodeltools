push $R0
push $R1

${REReplace} $R1 "\\" "$INSTDIR" "/" 1

FileOpen $R0 "$ECLIPSEHOME\plugins\com.symbian.smt.gui.properties\location.properties" w
FileWrite $R0 "location = $R1\\SystemModelGenerator"
FileClose $R0

pop $R1
pop $R0