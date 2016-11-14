# csci4401-pa2
A simple thread pool facility for Java threads.

Utilizes a thread pool to expose a message interface. Sending a message to the pool triggers the creation of a thread with the initial parameters contained in the message.

* The thread is created on demand and performs the necessary computation; once complete, the threads finishes (by reach the end of its run() method).
* The result of the computation is placed as an object in the result queue.
* Includes complete implementation of BasicMsgQ, MatrixMultiplyServiceWorker, and MatrixDeterminantServiceWorker.
* All classes are part of the csci4401.service package.
* MatrixMultiplyServiceTest and MatrixDeterminantServiceWorkerTest classes demonstrates that the code works correctly.
* Test classes use varying matrix sizes and the number of iterations for both sequential and serial executions.
* tmader-pa2-report.pdf includes observations and reasonings about the results.
