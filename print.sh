#!/bin/bash
a2ps -R -s1 --media=letter --columns=1 --rows=1 --chars-per-line=80 --major=rows --border=no --tabsize=3 src/parser/* src/parser/expression/* src/parser/statement/* tests/*.ll -o - | ps2pdf - Program.pdf
pdfunite write-ups/Project3WriteUp.pdf Program.pdf Printout.pdf
rm Program.pdf
