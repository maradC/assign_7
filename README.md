# assign_7

Problem:

We have two philosophers (Phil 0 and Phil 1) sitting at a table.

There are two forks, one between each pair of philosophers.

Each philosopher needs two forks to eat, but the forks are shared between them.

The philosophers alternate between thinking and eating:

A philosopher thinks for a while (simulated by sleep()).

A philosopher eats for a while (by acquiring both forks).

We need to avoid deadlock while ensuring that both philosophers can eat.

Requirements:

Use two mutex locks or semaphores to ensure mutual exclusion in accessing forks.

Implement deadlock-free synchronization for the two philosophers.
