# Synalogik Application by Matt Millward

## Build
Build with the command ```mvn package```

## Run 
Run with the command ```java -cp target/MMillwardSynalogik-1.jar synalogik.App <filename>``` where ```<filename>``` is to be replaced with the path to the file you want to test.

## Assumptions
- New lines do **not** indicate the end of a word. e.g.

```
progr
amming
```
will be counted as a single word
- White space indicates the end of a word.
- The following list of punctuation constitutes the end of a word ```. , ; : ? ! " ) (```
- Any other character is considered to be part of a word.
- Files use line breaks regularly to split text over multiple lines.

## Example words

 - **MyLovelyWord** = 1 twelve letter word
 - **Don't** = 1 five letter word
 - **A,Comma,Seperated,List** = 1 of each of 1,4,5,9 letters
 - **My words** = 1 of each of 1,5 letters
 - **Line\nBreak** = 1 nine letter word