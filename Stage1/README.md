# Stage 1

For extracting appropriate regions from the frontal image I have used the following approach.
Before extracting the binary regions, I have used the Mean filter with radius 4 and the Gaussian
kernel of size 5x5 with sigma equal to 4. After the region extraction, I have used the mean kernel
in order to smoothen the overall extraction and eliminate the clutter. Below is the structure of the
Gaussian kernel used.


| 0 | 1 | 2 | 3 | 4 |
|:--------:| -------------:| -------------:|  -------------:|  -------------:|
| 0.035228 |	0.038671 |	0.039892 |	0.038671 |	0.035228 |
| 0.038671 |	0.042452 |	0.043792 |	0.042452	| 0.038671 |
| 0.039892 |	0.043792 |	0.045175 |	0.043792 | 0.039892 |
| 0.038671 |	0.042452 |	0.043792 |	0.042452	| 0.038671 |
| 0.035228 |	0.038671 |	0.039892 |	0.038671	| 0.035228 |

This is the kernel used for any picture before extracting layers.

| 0 | 1 | 2 | 3 | 4 |
|:--------:| -------------:| -------------:|  -------------:|  -------------:|
| 1 | 1 | 1 | 1 | 1 |
| 1 | 5 | 5 | 5	| 1 |
| 1 | 5 | 10 | 5 | 1 |
| 1 | 5 | 5 | 5	| 1 |
| 1 | 1 | 1 | 1	| 1 |


## Rotations and Smiles

During the rotations and smiles the results remain somehow stable. In my case, the ears of girls are covered by their hair and they are not included in second and third layers. The full rotation of the head to left and right still gives appropriate results. The smiles don't actully affect the results that much, as the teeth are mainly white and their corresponding values fall outside the all the gamult fileds ranging from f(0,1) to f(2,3).
