[![Gitter](https://badges.gitter.im/Join Chat.svg)](https://gitter.im/graphtheorysoftware/GraphTea?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

![Preview](http://github.com/graphtheorysoftware/GraphTea/raw/master/src/presentation/peterson.png)

#YouTube demo
see this [video](http://www.youtube.com/watch?v=0gblxDCNsmY)

#GraphTea
GraphTea is a software framework to work on graphs and social networks. 
It helps you to:
- draw a graph
- get reports about it
- run algorithms on it
- visualize it

[DOWNLOAD GraphTea](https://github.com/graphtheorysoftware/GraphTea/zipball/master)

it runs under window/linux/mac osx (based on java).

#RUN
execute `run.bat` (windows), or `run.sh` (linux/mac).

manually:
> java -jar graphtea-main.jar

#DEVELOP
for working on the source, 

1. make your changes
2. run make.sh (or type in terminal `./make.sh`)
3. it will compile your changes and run the application.


manually:

1. open terminal
2. `cd src/scripts`
3. `ant`. this will build the application for you in the binary folder.
4. for seeing your changes do the steps described for "run"

note that you should have [appache ant](http://ant.apache.org/) installed.

#Write a graph algorithm,
go change this file: [SampleAlgorithm.java](https://github.com/graphtheorysoftware/GraphTea/blob/master/src/graphtea/extensions/algorithms/SampleAlgorithm.java).
you can make also reports, generators, file formats and actions.

#What can you do with graphtea?

* Drawing your graph with mouse or using predefined graphs (under graph > generate), like trees, complete graphs, stars, generalized peterson and etc.
* Getting information about your graphs (under graph > reports menu), like num of connected components, chromatic number, independence number, girth size, num of triangles and etc.
* Run algorithms step by step on your graph and see how they work. This is very usefull for teaching graph algorithms. You can pause, and it shows the current state of algorithm by coloring edges and vertices.
* Visualizing your graphs. You made a social network from your database and want to represent it in a meaningfull way? Use the visualizations.
* Presenting your graphs in your papers, websites or reports. GraphTea has a wide range of options to draw graphs, having different colors for edges and vertices, different borders, fonts, and sizes and etc. When you finish drawing your graph, you can save to a image file or even to a Tex document to put in your report.

* making new graph generators, graph reports, file types, actions, algorithms by writing extensions. Extensions provide a gateway to add new functionalities as simple as putting a file to extensions directory. You can write them using Java and Matlab. For more samples take a look at extensions directory.

# Need help?
more docs will come soon. if you have any questions just post it in the issues and i will write you back as soon as possible.

# CONTRIBUTE
(http://help.github.com/send-pull-requests/)

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Added some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request

have fun!





---
###credits

- Current Developers:
  - Mohammad Ali Rostami ali [at] c3e [dot] de
  - Azin Azadi aazadi [at] gmail [dot] com
  - E. Suresh  sureshkako [at] gmail [dot] com

- GraphTea is base on GraphLab, a software developed in Sharif university of Technology [0].
  - Supervisor: Dr. Amir Daneshgar [1]
  - Contributors: Azin Azadi, Ruzbeh Ebrahimi, Omid Aladini, Reza Mohammadi, Mohammad Ali Rostami [2], Mina Naghshnejad, Ali Ershadi, Soroosh Sabet, With Thanks to: Soheil Siadatnejad



[0]: http://www.sharif.ir

[1]: http://sharif.edu/~daneshgar/

[2]: http://c3e.de/team/arostami

