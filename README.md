Story Language Mashup
===================
Are you fluent in or currently learning a foreign languange?   Have you ever wondered how that language is natively spoken?  Do you want to embrace yourself in another culture and sound intelligent when conversing with the locals?  

The Story Language Mashup Android application will allow you, regardless of your language skills, to understance the most recent lingo being spoken in a foreign culture while staying up to date on foreign politics.

How to use the app:
--------------
  Upon start up, the application will display news articles from various RSS feeds.  Click on an article that you find interesting and instantly you will be able to read the article in your targeted foreign language.  Test how well you know the language by trying to read the article all the way through.  Too difficult? Use the slider bar at the bottom of the page to adjust how much of the story you want to see in your native language.
  
Before translating the entire page back into your native language, leave parts of the story in both your native and foreign language.  Then try using the native language context to translate sentences of the foreign language.  That's what I call a MASHUP!  Even if you struggle at first to translate a foreign sentence, get familiar with some of the words that are being used regularly in the media.  And at some point don't forget to focus on the subject matter of the story; international politics binds all cultures together.  Understanding events in the rest of the world will be the first steps in embracing your target culture.      

Additional notes:
--------------
  * The stories are pulled from the Web in the foreign language and then translated into your native language using the 
Microsoft Translator Api.  This guarantees that the sentences read in your target language will have minimal grammatical mistakes (fully dependent on the Author of the story).  On the other hand the sentences read in your native language depend on the accuracy of the Microsoft Translator Api.

Source code organization
--------------
  1.  activity - All activity pages seen within the app.
  2.  adapter - Holds any adapters used by app (ListView, Fragment, etc.)
  3.  debug - Any classes that are used for debugging (MyLogger, etc.)
  4.  forms - Objects that are derived from online files such as Rss feeds or Html files
  5.  interfaces - any interfaces I have written
  6.  parse - Everything related to the parsing of either XML or HTML.  Parsers extend base class ResourceParser and only properly formed XML may use my SAX extended XmlContentHandler
  7.  translate - Classes used to physically translate text between two languages.
  8.  webresource - Classes devoted to fetching online resources (Rss, Html, etc.) and parsing them into a collection of Objects (see forms).
  9.  other - Anything else (Controllers, etc.) 


