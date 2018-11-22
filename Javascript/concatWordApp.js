const Queue = require('./queue')
const fs = require("fs");
/*
    WordApp:
    Author: Patrick Dizon 3/6/2016

    This script provides the following results:
    - Longest words,
    - Second longest words
    - The number of longest words

*/


class WordApp {
    constructor() {

    }
}

// WordApp.prototype.findLongestConcatenatedWords = function(file) {
//     console.log(file) 

// }
WordApp.prototype.do = function(file) {
    // const startTime = Date.now();
    console.time('test')
    this.findLongestConcatenatedWords(file);
    console.timeEnd('test')
    //const stopTime = Date.now();
    //const elapsedTime = stopTime - startTime;
    //console.log("runtime:" + elapsedTime );//, stop - start 
    
}

WordApp.prototype.isConcat = function(word, wordsDict ) {
    /*
        Function that loops over found suffs
    */
    //Whole word
    w = new Word(word, wordsDict);
    //set initial queue to use for looping, faster instead of a list, cause of shifting
    const queue = new Queue();

    w.getSuffs().forEach( suf => {
        queue.add(suf);
    })

    
    //const it = queue.iterator();
    
    while (queue.size() > 0) {
        
        const suf = queue.remove();
        if (suf && suf.getFound() == true) {  
            //return True
            return suf.getFound();
        }else {
            //Check the mids and add to the queue to keep looking
            w2 = new Word(suf.getSuf(), wordsDict);
            w2.getSuffs().forEach(suf2 => {
                queue.add(suf2);
            })
        }
    }
    return false;
}

WordApp.prototype.findLongestConcatenatedWords = function(file) {
    /*
        Function that reads and loops over words.txt file
    */
    wordList = [];
    longestWords = []; //list to store longest concatenated words
    secondLongestWords = []; //list to store second longest concatenated words
    count = 0; //list to store longest concatenated words
    fileStream = fs.readFileSync(file, "utf8")
    
    wordList = fileStream.split('\r\n')
    //Set<String> wordsDict  = new HashSet<String>(); 
    wordsDict = new Set(wordList)
    // wordList.forEach( w => {
    //     wordsDict[w] = true;  
    // })
    
    wordList.sort(function(a, b){
        // ASC  -> a.length - b.length
        // DESC -> b.length - a.length
        return b.length-a.length;
    });
    //loop through list of words
    wordList.forEach(word => { //.subList(0,1)){
        if(this.isConcat(word, wordsDict)){
            //There maybe 1 or more longest or second longest words,
            //so add to list if the list is empty or the same length
            //as the first word
            if(longestWords.length == 0 || word.length == longestWords[0].length){
                longestWords.push(word);
            } else if (secondLongestWords.length== 0 || word.length == secondLongestWords[0].length) {
                secondLongestWords.push(word);
            }
            //set counter
            count++;
        }
    })

    console.log("Longest word is: " + longestWords.join(",")); // + .format(longestWords)
    console.log("Second longest words: " + secondLongestWords.join(",")); //.format(secondLongestWords)
    console.log("Total Concatenated words is: " + count); //.format(count)
}

class Suf {
    constructor(suf,found) {
        this.suf = suf;
        this.found = found;
    }

    getFound() {
        return this.found
    }

    getSuf() {
        return this.suf
    }
}

class Word {
    /*
        Word object stores a word that needs to be evaluated by is_concat function.
    */
    constructor(word, wordsDict) {
        this.prefs = [];
        this.suffs = [];
        this.word = word + '';
        this.pref_word = word + '';
        
        //get possible prefixes and suffixes from large to smaller words
        var i
        for(i = 2; i < this.pref_word.length; i++) {
            const pref = this.pref_word.substring(0,i);
            if (wordsDict.has(pref)) {
                this.prefs.push(pref);
            }
        }
    
        var i;
        for(i = 0; i < this.prefs.length; i++) {
            //get the suffix
            const suf = this.pref_word.substr(this.prefs[i].length);
            //check the wordsDict and length of the suf word
            if(wordsDict.has(suf) || suf.length == 0) { 
                this.suffs.push(new Suf(suf,true));
                //break as soon as one is found to speed up the process
                continue;
            } else {
                //else store the suf as it may contain a concatenated word
                this.suffs.push(new Suf(suf,false));
            }
        }
    }
}

Word.prototype.getSuffs = function() {
    //Suffs Getter
    return this.suffs;
}

const app = new WordApp()
app.findLongestConcatenatedWords("words.txt")