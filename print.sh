#!/bin/bash
a2ps -R -s1 --media=letter --columns=1 --rows=1 --chars-per-line=80 --major=rows --border=no --tabsize=3 src/parser/* src/parser/expression/* src/parser/statement/* tests/* print.sh -o - | ps2pdf - Program.pdf
pdfunite write-ups/Project2WriteUp.pdf Grammar-MkII.pdf First-Follow-MkII.pdf Program.pdf Printout.pdf
rm Program.pdf
