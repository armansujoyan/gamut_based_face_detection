# Lip detection

Using statistical approach that 3rd layer of the extraction clusters the pixels of the lips mainly, we would use some specific filtering in order to enforce this effect and than calculate the centroid for the extracted binary layer i.e. centroid of the lip and draw the bounding box of the lip. There is also a great paper which uses YCbCr color space in order to extract the most relevant lip pixels and then uses a clustering algorithm in order to get the corresponding pixels. Below is the link of the paper.

[Color based lip method](https://www.researchgate.net/publication/210333356_Color-based_Lip_Localization_Method)