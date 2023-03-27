# BEST Course Applications JSON Parser

## How To Use
1) Export course motivation letters from the BEST Private Area
    - Sort by: any
    - Format: PDF
    - Page breaks between students: yes
2) Put the PDF in `best-course-applications-json-parser` folder
3) Rename the PDF to `letters.pdf`
4) Open a console in this folder (e.g. type `cmd` in the address bar)
5) Run the first command: `node motivation_letter_pdf_parser.js`
    - Prerequisite: have node.js installed
6) Run the second command: `java MotivationLetterJsonParser`
    - Prerequisite: have Java installed
7) You can find the parsed applications in the newly created `applications` folder :)
    - Applications are 1-indexed and sorted like in the original PDF
    - There is an additional `0.json` which is a JSON list with all applications for your convenience