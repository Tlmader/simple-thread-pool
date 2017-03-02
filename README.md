# simple-thread-pool

**Principles of Operating Systems I Assignments 2 & 3***

A simple thread pool facility for Java threads.

## Info
The program utilizes a thread pool to expose a message interface. Sending a message to the pool triggers the creation of a thread with the initial parameters contained in the message.

* The thread is created on demand and performs the necessary computation. Once complete, the threads finishes by reaching the end of its run() method.
* The result of the computation is placed as an object in the result queue.
* Test classes use varying matrix sizes and the number of iterations for both sequential and serial executions.
* tmader-pa2-report.pdf includes observations and reasonings about the results.
