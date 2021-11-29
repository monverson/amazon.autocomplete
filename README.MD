# Amazon AutoComplete Score Estimator
# Overview
The following repo estimate the popularity of amazon search volume.

# Running Instruction
* You have to had jdk 8 <br>
* Run with mvn clean install
* Run the main Application.java class
* Send postman request with your search keyword ex : http://localhost:8080/estimate?keyword=iphone+charger 

# What You have to deliver
1) What assumptions did you make?
    My assumption is calculate the frequency of seach text in the autocomplete results.  
2) How does your algorithm work?
    * I have one endpoint and log information method. The endpoint checks how many maximum words matched with the suggestion list and calculate the frequency as a score.
      For example : my search text is iphone+charger+13 and autocomplete result list is: [iphone charger, iphone 13, iphone 15 case] then after compare each text
      maximum matched words are: **iphone charger**  and score calculated as a frequency : (matches * 100 / keywordWords.length); 200/3 = 66
    * The log information method prints all prefix score like : 
      i-> 0
      ip-> 0
      iph-> 0
      ipho-> 0
      iphon-> 0
      iphone-> 100
      iphone c-> 50  
3) Do you think the hint that we gave you earlier is correct and if so - why?
   - I think it's not correct. If you open amazon.com and search any game name for instance playstation fifa most of the time the latest version comes up at the top, because the latest versions have always been popular in searches.
4) How precise do you think your outcome is and why?
   - It's hard to tell. This project works as usual score algorithm. I'm sure that amazon uses more complex logic. Generally they are using trie data structure and prefix hash table algorithm.