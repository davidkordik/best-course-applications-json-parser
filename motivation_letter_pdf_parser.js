import fs from 'fs'
import pdfParser from "pdf2json"

const parser = new pdfParser(this, 1)

parser.on("pdfParser_dataError", errData => {
    console.error(errData.parserError)
});

parser.on("pdfParser_dataReady", pdfData => {
    fs.writeFileSync("./letters.txt", parser.getRawTextContent())
	console.log("[motivation_letter_pdf_parser] Success")
});

console.log("[motivation_letter_pdf_parser] Generating letters.txt ...")
parser.loadPDF("./letters.pdf")