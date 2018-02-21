"""
    ConcatWordsApp:
    Author: Patrick Dizon 3/6/2016

    This script provides the following results:
    - Longest words,
    - Second longest words
    - The number of longest words

"""
import timeit
import itertools
from collections import deque 

class Word(object):
    '''
        Word object stores a word that needs to be evaluated by is_concat function.
    '''
    word = '' #Word to match
    prefs = [] #Possible beginning of words
    suffs = [] #Possible ending of words


    def __init__(self, word, words_dict):
        self.word   = word
        self.prefs  = []
        self.suffs   = []

        #get possible prefixes and suffixes from large to smaller words
        for x in reversed(range(len(self.word))) :    
            if self.word[0:x] in words_dict:
                self.prefs.append(self.word[0:x])
        
        
        for pref in self.prefs:
            #get the suffix
            suf = self.word[len(pref):]
            #check the words_dict and length of the suf word
            if suf in words_dict or len(suf) == 0:
                self.suffs.append((suf,True,))
                #break as soon as one is found to speed up the process
                break
            else:
                #else store the suf as it may contain a concatenated word
                self.suffs.append((suf,False,))

def is_concat(word, words_dict, index=0):
    """
        Function that loops over found suffs
    """
    #Whole word
    w = Word(word, words_dict)
    #set initial queue to use for looping, faster instead of a list, cause of shifting
    q = deque(w.suffs)
    while q:
        m, t = q.popleft()
        if t:  
            #return True
            return t
        else:
            #Check the suffs and add to the queue to keep looking
            w2 = Word(m, words_dict)
            q += w2.suffs
    return False


def find_longest_concatenated_words(file):
    """
        Function that reads and loops over words.txt file
    """
    longest_words = [] #list to store longest concatenated words
    second_longest_words = [] #list to store second longest concatenated words
    count = 0 #list to store longest concatenated words

    with open(file, 'r') as f:
        #set lines to list but filter out empty lines and strip \r\n from the values 
        word_list = list(filter(len, [ l.strip() for l in f.readlines()]))
        
        #convert to frozenset for faster lookup.
        words_dict = frozenset(word_list)
        
        #reverse the list so that we can eval the top faster
        word_list = list(reversed(sorted(word_list, key=len)))
        
        #loop through list of words
        for word in word_list:
            if is_concat(word, words_dict):
                #There maybe 1 or more longest or second longest words,
                #so add to list if the list is empty or the same length
                #as the first word
                if len(longest_words) == 0 or len(word) == len(longest_words[0]):
                    longest_words.append(word)

                elif len(second_longest_words) == 0 or len(word) == len(second_longest_words[0]):
                    second_longest_words.append(word)
                #set counter
                count += 1

        print ('Longest word is: {0}'.format(longest_words))
        print ('Second longest words: {0}'.format(second_longest_words))
        print ('Total Concatenated words is: {0}:'.format(count))

#runs the code
start = timeit.default_timer()
find_longest_concatenated_words('words.txt')
stop = timeit.default_timer()

print ('runtime:', stop - start)

    