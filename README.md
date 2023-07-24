# Julia
Visualization for Julia sets

## What are Julia Sets
For a great brief explanation of Julia sets check out this website: https://www.mcgoodwin.net/julia/juliajewels.html

## Math Overview
The program has a few variables that users can adjust to produce different Julia sets. The most important are A, B, iterations, and order. 
  
For simplicity's sake, let's denote order as $n$ and iterations as $t$.  
The program will then compute the following recursive relation on the complex plane  
$z_{k + 1} = z_{k}^n + A + Bi$  
Where $z_j = x + yi$, with x and y representing coordinates on a 2D plane.
This recursive relation is computed until $|z_k|$ becomes too large or has exceeded many recursions (i.e. $k > t$). Essentially, this will check whether the recursive relation is bounded or not.  
If the relation is bounded, then we set the point $\(x, y\)$ to be black, otherwise, we set the hue of the point as a function of the number of recursions before the relation exploded in magnitude.

## Variables Explained
A, B, iterations, and order were explained in the previous sections.  
The next four variables control how the visualization will look.  
### Hue
The hue of a point that is unbounded is calculated according to the following equation  
$H * t / (t - k)$  
Where $t$ is the maximum number of iterations allowed, $k$ is the number of iterations before $|z_k|$ became too large, and $H$ is the hue constant multiplier.  
### Zoom, Saturation, Brightness
The next three variables are fairly self-explanatory. Together the hue, saturation, and brightness are set for each point and then converted into RGB values.
