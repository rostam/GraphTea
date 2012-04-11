![Preview](https://github.com/azinazadi/GraphLab/raw/master/presentation/peterson.png)
a short presentation: http://www.youtube.com/watch?v=0gblxDCNsmY
#GraphLab
GraphLab is a software framework to work on graphs and social networks. which consists of a graph library and a graph GUI. The library part is a framework designed for developing graph theory algorithms and testing graph conjectures. and the GUI part aimed to draw and visualize graphs and running algorithms on them. The program is based on plug-ins and extensions and provides a user friendly application platform to create scientific applications.

it runs under any platform (based on java).

##run
to run the application, goto the binary folder and execute `run.bat` (windows), or `run.sh` (linux/mac).
you can run it directly by

> java -jar graphlab-main.jar

##how to use it
see this [video](http://www.youtube.com/watch?v=0gblxDCNsmY)

##compile
for working on the source, 

1. make your changes
2. open terminal
3. `cd src/scripts` 
4. `ant`. this will build the application for you in the binary folder.
5. for seeing your changes do the steps described for "run"

note that you should have [appache ant](http://ant.apache.org/) installed.

##write a graph algorithm
go change this file:

##what can you do with graphlab

* draw your graph with mouse or using predefined graphs (under graph > generate), like trees, complete graphs, stars, generalized peterson and ...
* get information about your graphs (under graph > reports menu), like num of connected components, chromatic number, independence number, girth size, num of triangles and ...
* run algorithms step by step on your graph and see how they work. this is very usefull for teaching graph algorithms. you can pause, and it shows the current state of algorithm by coloring edges and vertices.
* visualize your graphs. you made a social network from your database and want to represent it in a meaningfull way? use the visualizations
* present your graphs in your papers, websites or reports. GraphLab has a wide range of options to draw graphs, having different colors for edges and vertices. different borders and fonts and sizes and ... when you finish drawing your graph, you can save to a image file or even to a Latex document to put in your report. then you can use latexcad app, to further refine your graph.

* you can make new graph generators, graph reports, file types, actions, algorithms by writing extensions. extensions provide a gateway to add new functionalities as simple as putting a file to extensions directory. you can write them using Java and Matlab. moreover you can write new extensions using any programming language that supports [redis](http://redis.io/clients) which includes almost any wellknown programming language. for more samples take a look at extensions directory

##need help?
more docs will come soon. if you have any questions just post it in the issues and i will write you back as soon as possible.

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Added some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request

have fun!





---
###credits
GraphLab is developed in Sharif university of technology. it is under GPL license.

project supervisor:
Dr. Amir Daneshgar

contributors:
Azin Azadi,
Ruzbeh Ebrahimi,
Omid Aladini,
Reza Mohammadi,
Mohammad Ali Rostami,
Mina Naghshnejad,
Ali Ershadi,
Soroosh Sabet

With Thanks to: Soheil Siadatnejad