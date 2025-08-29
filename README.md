## A pixel learning recognition microalgorithm API model based on the CNN convolutional neural network structure theory and developed using pure native Java
## 一个基于CNN卷积神经网络结构理论使用纯原生Java开发的像素学习识别微算法API模型

<br>
<br>

# Get started quickly! (快速上手)

## Download (下载地址)

## https://github.com/BProbie/api/tree/MyEyes/release

## https://github.com/BProbie/api/raw/refs/heads/MyEyes/release/MyEyes.jar

<br>

## The API mainly maintain three objects (主要维护)

```java
MyEyes eyes = Myeyes.getInstance(); // com.probie
```

```java
Picture picture = new Picture(); // com.probie.Picture || com.probie.Factory.PictureFactory
```

```java
Pictures pictures = new Pictures(); // com.probie.Pictures || com.probie.Factory.PicturesFactory
```

<br>

## How to study pictures?  (学习)

```java
// Study a picture
Picture picture = new Picture("D:\\pictures\\a.png");
// Because the file name is a.png so the value is defalut "a"
// But you can also change the value
picture.setValue("a");
// Even add another value
picture.addValue("A");
// When you use study() the data will be stored in the current directory file
// The database file maybe named ".\MyEyes\data\a.data"
picture.study();

// Study more Pictures
Pictures pictures = new Pictures("D:\\pictures\\*.png");
pictures.study();
```

<br>

## How to identification any picture? (识别)

```java
// You should indentification before study or hava any database files in current directory!
Picture picture = new Picture("D:\\pictures\\x.png");
System.out.println(picture.guess()); // if the picture is a the result maybe: "a"
```

<br>

## You can use chain to code! (链式编程)

```java
Picture picture = new Picture().setPicture("D:\pictures\cat.png").setValues(new Object[] {"cat","Cat"}).addValues(new Object[] {"mao","Mao","猫"}).removeValue("猫").study().clone();

// Support url!
System.out.println(picture.setPicture("https://raw.githubusercontent.com/BProbie/api/refs/heads/MyEyes/MyEyes/Picture/cat.png").clone().guess());
```

<br>
<br>

# More functions! (更多功能)

## Match with others (与其他图像对比)

```java
// Get current computer screen
Picture screen = MyEyes.getScreen();
Picture cat = new Picture("D:\\pictures\\cat.png");

if (picture.isInPicture(screen)) {
    int x = picture.whereInPicture(screen)[0]; // The x is default at the Top left corner
    int y = picture.whereInPicture(screen)[1]; // The y is default at the Top left corner
    System.out.printf("The %s is in the (%d,%d)",cat.getSimpleName(),x,y);
}
```

<br>

## Maybe we can use cache to boost the speed! (缓存)

```java
Picture cat = new Picture("D:\\pictures\\cat.png");
if (!cat.isCache()) {
    cat.cache(); // Then we cat more faster get the information of the picture next time
}
```

<br>
<br>

# Last but not least! (边角料)

## You can DIY some import values (重要参数)

```java
MyEyes eyes = MyEyes.getInstance();

eyes.setRGBMistakeRange(10); // When match with other image the mistake range of rgb
eyes.setRGBMistakeRate(10); // When match with other image the judge rate, 10 <=> 10%
eyes.setGuessMistakeRange(10); // When identification image the mistake range of feature rgb
eyes.setGuessMistakeRate(60); // When identification image the judge rate, 60 mean 60%

// When we identification a picture we compress its rgb
// The 40 mean from 453×285 to 40×40 rgb
eyes.setSimpleSize(40);

// Before we compress its rgb we will amplify its
// The 3 mean times
// For example 200×100 -> 600×300 -> 40×40 rgb
eyes.setAmplifyTime(3);
```

<br>

## Another parameter may few be used (不重要的方法、形参)

```java
Picture picture = new Picture(BufferdImage);
Pictures pictures = new Pictures(BufferdImage[]);

BufferdImage bufferdImage = picture.getBufferdImage();
BufferdImage bufferdImage = MyEyes.getBufferdImage("D:\\pictures\\cat.png");

HashSet<Picture> pictureSet = pictures.toHashSet();
Pictures pictures = picture.toPictures();

int[][] rgb = picture.getRGB();
int[][] simpleRGB = picture.getSimpleRGB();

String name = picture.getName();
String simpleName = picture.getSimpleName();

String uid = picture.getUID();
```

<br>

## Some explanation! (重要说明)

```java
// The UID mean Unique ID Each file just has one same UID
String uid = picture.getUID();

// The rgb is full rgb so it will be little big
// Bug it connect with its uid
// So you need not to be worry about repeatly get will waste performance
int[][] rgb = picture.getRGB();

// The simple rgb is the feature rgb is small maybe just 1-5 kb
int[][] simpleRGB = picture.getSimpleRGB();

// We have more define to choose more files!
Pictures pictures = new Pictures("D:\\pictures");
Pictures pictures = new Pictures("D:\\pictures\\");
Pictures pictures = new Pictures("D:\\pictures\\*");
Pictures pictures = new Pictures("D:\\pictures\\*.png");
Pictures pictures = new Pictures("D:\\pictures\\*.jpg");

// "D:\\pictures\\"
// We can named our train files just like
// "0-0.png" "0-1.png" "0-2.png" "0 - ababa.png" We can import them default "0"
```

<br>
<br>

# Deeply use it! (深入探索)

## If you want to explore the API in depth, download the MyEyes.jar file in the release folder and play with it, so you can experience more as you go. Or you can also try reading the source code, activate your brain and explore more ways to play by yourself. If you have better optimization suggestions or update content, please post your opinions in issues!

<br>
<br>

# Implementation principle (原理)

## It is based CNN(卷积神经网络结构算法) if you want to more rigorously study it you can depend on www.baidu.com

## In here I want to share with you its thoughts more simple

## At first I have an image then I want to know what is it, I should had seen it before.

## So we see it and study it by its RGB

## But just with RGB is too big and inaccurate we need some thoughts!

## Based on RGB we can amplify it firstly because we don't know where is the main image maybe on the top? Or at the left, right? To Amplify, we center the main image!

## For example:

## 000	000	->	000000000  000000000

## 100	001	->	000100000  000001000

## 000 	000	->  000000000  000000000

## 3×3 -> 9×9

## Then we should compress it while get its feature RGB

## In the project I use the average algorithm to get its feature RGB named simple RGB

## For example:

```java
average = (average+amplifyRGB[x][y])/2;
```

## Then we get:

## 000	000  ->  000000000  000000000

## 100	001	 ->  000100000  000001000  ->  100 001

## 000 	000	 ->  000000000  000000000

## 3×3 -> 9×9 -> 1×1


## We store the data in the local database

## When we use guess() we import the database and check it with our image's simple RGB or feature RGB

## We collect all the values which enough the mistake and choose one best then return it!

## Finally we get the result!

<br>
<br>

# Postscript (后记)

## API-Name: MyEyes

## API-Version: 1.0

## Language: Java

## JDK-Version: 21.0.8

## Dependence: null

## Author: Probie

## Thanks: []



# Learn More About (更多内容请关注)


# https://github.com/BProbie


