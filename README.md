# Due date calculator

[![Build Status](https://travis-ci.org/miklos-martin/due.svg?branch=master)](https://travis-ci.org/miklos-martin/due)

Two main ideas were
* abstract away what are the working hours
* create a time-stream within working hours starting from the submission date and consume it while the task will be in progress

Run tests with `sbt test`

## Manual testing

Run `sbt run`

An interactive shell lets you try out the algorithm manually

```
Hey, gimme the submit date and the time the task will take
Enter empty line to QUIT

Usage:
  SUBMIT_DATE, TURNAROUND_TIME => DUE_DATE

e.g.

  > 2016-12-16T10:31:00, 4d 3h
  2016-12-22T13:31:00

>
```
