# Concatenated Words

## The challenge

We have provided a file called “words.txt” which contains a sorted list of approximately 173,000 words. The words are listed one word per line, do not contain spaces, and are all lowercase.

Your task is to write a program that reads the file and provides the following:
- the longest concatenated word (that is, the longest word that is comprised entirely of
shorter words in the file)
- the 2nd longest concatenated word
- the total count of all the concatenated words in the file

For example, if the file contained: 
	- cat
	- cats 
	- catsdogcats
    - dog
    - dogcatsdog
	- hippopotamuses
    - rat
	- ratcatdogcat

the longest concatenated word would be 'ratcatdogcat' with 12 characters. ‘hippopotamuses’ is a longer word, however it is not comprised entirely of shorter words in the list. The 2nd longest concatenated word is ‘catsdogcats’ with 11 characters. The total number of concatenated words is 3. Note that ‘cats’ is not a concatenated word because there is no word ‘s’ in the list.

## Your solution
Please email your solution source code as attachments (zipped if more than 3 files). In addition, please include the following details in the body of the email:

1. the longest and 2nd longest concatenated words
2. the total count of concatenated words in the file
3. the programming language(s) you used to complete the challenge

Feel free to also include any comments you have on the approach you took. Solutions in one (or more) of the following languages are preferred: C, C++, Java, JavaScript, Python, Ruby. If you have any questions about the problem or would like to use a language not listed here, please feel free to contact us.
This is your opportunity to demonstrate your problem­solving abilities, coding skills, and knowledge of software engineering principles in a way that is difficult to do in an interview. In addition to speed and accuracy, your solution will be judged on elegance, efficiency, and style.


# This script determines
- Longest concatenated words
- Second concatenated longest words
- The number of long concatenated words

To run ConcatWordsApp.py in terminal type:

```$ python ConcatWordsApp.py```

or 

```$ java -cp . ConcatWordsApp```
