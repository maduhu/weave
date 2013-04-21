What is Weave ?
===============
Shouldn't running distributed application on YARN be as simple as running threads on JVM. If you agree to that, then you 
looking at the right project. Weave is a framework that simplifies building applications on YARN. Even though YARN started
as necessity for improving MR framework, it's not just limited to MR anymore. More complex applications can be built on 
YARN. Weave defines a new programming model for running applications on YARN.

Goals
=====
 * Not designed to be a replacement for YARN

   Designed to simplify building, debugging & running of YARN Applications

 * Running in YARN should be as simple as running Threads in Java

    A simpler programming model for YARN

 * Hide details of YARN  

   + Simplified API for specifying, running & managing an application
   + Simplified way to specify and manage stage(s) of the application lifecycle
   + Generic Application Master to better support simple applications

 * Simpler archive management
 * Better control over application logs and errors
