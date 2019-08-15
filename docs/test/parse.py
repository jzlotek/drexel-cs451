import csv

lines = []
print(r'\begin{tabularx}{\textwidth}{| c | l | l | l | l | l |}')
with open('rules.csv', 'r') as _file:
    f = csv.reader(_file)
    for index, line in enumerate(f):
        if index == 0:
            print(line)
            l = ['\\textbf{' + str(e) +'}' for e in line]
            print(" & ".join(l) + r'\\ \hline')
        else:
            print(" & ".join(line) + r'\\ \hline')

print(r'\end{tabularx}')

