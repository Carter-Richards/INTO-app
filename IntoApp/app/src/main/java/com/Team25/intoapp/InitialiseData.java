package com.Team25.intoapp;

import java.util.LinkedList;

/**This class is being used to initialise the data in place of the server providing this information as we were unable to host the server
 */

public class InitialiseData {

    public static InformationManager InitData(String input){
        InformationManager returnInfo = new InformationManager(input);
        LinkedList<InformationObject> list = new LinkedList<>();
        switch(input){
            case "notifications":
                InformationObject notif1 = new InformationObject("GIAG Archery","Come test your aim with Newcastle Universities very own Archery Club, limited spaces available.","","","");
                list.add(notif1);

                InformationObject notif2 = new InformationObject("Summer Ball","PsychSoc's End of Year Summer Ball The Life Centre","","","");
                list.add(notif2);

                InformationObject notif3 = new InformationObject("Legends Presents... The Ultimate Legends","LEGENDS Present The Ultimate Legends The North's ELiTE Celebrates Once Again Hosting TWO rooms At A New Venue!","","","");
                list.add(notif3);

                InformationObject notif4 = new InformationObject("Newcastle United Comic Con 2020",
                        "Comic Con! Everyone's dream. Unfortunately society funds don't stretch to flying us to San Diego. However, they do stretch to taking us all to Newcastle Comic Con. If enough people can make it then we will work out a group discount for us","","","");
                list.add(notif4);

                returnInfo.setInformationObjects(list);
                break;
            case "thingsToDo":

                InformationObject ttd1 = new InformationObject("Great North Museum: Hancock","If you're keen about natural history and ancient civlisations; then paying a visit to The Toon's very own museum would be nothing short of splendid for you.\n" +
                        "\n" +
                        "The site boasts displays which could make one learn about everything right from polar to interstellar exploration!\n" +
                        "\n" +
                        "Oh and yes, they have free entry!","54.9803 1.6130","https://www.whatsonnortheast.com/wp-content/uploads/2016/06/great-north-museum-hancock-venue.jpg","");
                list.add(ttd1);

                InformationObject ttd2 = new InformationObject("Tyneside Cinema","Have you ever seen depictions of people from the early to the middle era of the 20th century getting their news from a cinema hall?\n" +
                        "\n" +
                        "Well, Tyneside Cinema is the last such cinema from that era to still survive to this day. They boast an arthouse of both modern and vintage films. Besides that, it's a lovely independent cinema with three artsy cafes at their venue.",
                        "54.9803 1.6130","https://tynesidecinema.co.uk/wp-content/uploads/2019/09/WEBSITE-HEADER-IMAGES-TEMPLATE-2-1.png","");
                list.add(ttd2);
                InformationObject ttd3 = new InformationObject("Lane 7 Bowling","Lane7 isn't just another bowling alley, it's different!\n" +
                        "\n" +
                        "It's a bowling alley, a bar, a first class dining experience and a place with both ping-pong and snooker tables. It's all of that packaged into an amazing experience.\n" +
                        "\n" +
                        "All in all, it's an amazing concept and you should definitely go there.",
                        "","https://i2-prod.business-live.co.uk/incoming/article16974405.ece/ALTERNATES/s615/1_Lane7-Centre-North-East.jpg","");
                list.add(ttd3);
                InformationObject ttd4 = new InformationObject("Dog and Scone","Are you a dog lover? If the answer is yes then Dog and Scone is going to be a heaven for you!\n" +
                        "\n" +
                        "It's a unique café where you can enjoy  your coffee, tea or delicious cakes all in the company of their multiple resident dogs.\n" +
                        "\n" +
                        "It's a place where you can do both, relax and play with friendly four-pawed pooches.",
                        "","https://i.pinimg.com/originals/81/82/cf/8182cf9d9b81afdfebddb42a653e1584.jpg","");
                list.add(ttd4);
                InformationObject ttd5 = new InformationObject("Live Theatre","Newcastle's Live Theatre creates some of the most exciting, new work in Northern England.\n" +
                        "\n" +
                        "Their produced work is goes out to tour, both nationally and internationally. Their productions always end up getting awards which they rightfully deserve. \n" +
                        "\n" +
                        "Give this place a shot, you will absolutely love it!",
                        "","https://www.live.org.uk/sites/default/files/styles/lt_event_banner_crop/public/images/Plan_Your_Visit_main_pic_10.jpg?itok=UBK0x5f9","");
                list.add(ttd5);
                returnInfo.setInformationObjects(list);
                break;
            case "placesToEat":
                InformationObject pte1 = new InformationObject("Eat@Newcastle","Our cafés and restaurants are the perfect place to grab lunch, catch up with friends and meet colleagues. With a wide range of food and drink types catering to a variety of diets, we have something for everyone.\n" +
                        "\n" +
                        "At the INTO building we have a variety of beverages and hot international dishes, including full English and light breakfast, variety of fish/meat/veggie options and a selection of sides.",
                        "","https://images.justgiving.com/image/5202b732-a24f-4456-9df7-c34770345862.jpg?template=size250x250","");
                list.add(pte1);
                InformationObject pte2 = new InformationObject("Eat4Less","Eat4Less tops our list as it's a student favourite!\n" +
                        "\n" +
                        "With a large variety of options on their menu; including both healthy salads and yummy fast foods. This place offers everything.\n" +
                        "\n" +
                        "More importantly, the name of this place says it all. It's an absolute bang for the buck!",
                        "","https://10619-2.s.cdn12.com/rests/original/504_502137010.jpg","");
                list.add(pte2);
                InformationObject pte3 = new InformationObject("La Yuan","La Yuan, as they describe it is a modern, light filled serving zestful food from Sichuan province, China.\n" +
                        "\n" +
                        "They're said to be situated only a stone's throw away from Grey' Monument which is really close to the INTO building.\n" +
                        "\n" +
                        "So if you're ever craving authentic Chinese food, La Yuan is the place to be!",
                        "","https://www.crowdfunder.co.uk/save-la-yuan/og-image?f=true","");
                list.add(pte3);
                InformationObject pte4 = new InformationObject("YO! Sushi","If you're looking to have a modern Japanese experience on a budget and get some good food, YO! Sushi is the place to be.\n" +
                        "\n" +
                        "They're a Japanese restaurant that serve sushi and other such foods on a conveyor belt. They're present all across the UK and eating here is just an amusing experience.  ",
                        "","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQjqVc_DnD3zN0dh77LLrpqnI_eted5UH2kjH1KjFSC85Ro0gAf&usqp=CAU","");
                list.add(pte4);
                InformationObject pte5 = new InformationObject("Dabbawal","If you're ever craving curry or authentic Indian street food, Dabbawal has it all!\n" +
                        "\n" +
                        "An Indian restaurant located at High Bridge and Jesmond, Newcastle offers authentic Mumbai Street Food. Eating street food here is not only an enjoyable experience for your taste buds, but also a relaxing experience for you!",
                        "","https://live.staticflickr.com/5349/6897402970_6d3226b808_b.jpg","");
                list.add(pte5);
                returnInfo.setInformationObjects(list);
                break;
            case "nclEssentials":
                InformationObject ncle1 = new InformationObject("Grainger Market","Grainger Market is home to hundreds of traders and local businesses. This place boasts all, from jewellers, florists and cobblers to butchers grocers and artisan bakeries.\n" +
                        "\n" +
                        "When you visit Grainger Market, you can handpick fresh fruits, vegetables, fish and meat. Or even enjoy a meal right there in the market. All this for a very nominal cost.\n",
                        "","https://i2-prod.chroniclelive.co.uk/incoming/article16933048.ece/ALTERNATES/s1200/0_Grainger-Market-GV.jpg","");
                list.add(ncle1);
                InformationObject ncle2 = new InformationObject("Intu Eldon Square","Intu Eldon Square is one of the largest shopping centres in the UK. This shopping centres offers almost everything, from clothes, stationery and electronics to the most niche items like jewellery and watches.\n" +
                        "\n" +
                        "So if you're looking to do some shopping or just looking for something you need to buy, Intu Eldon Square is just a two minute walk away from the INTO Building.\n",
                        "","https://www.getintonewcastle.co.uk/images/facebook/uploads/ne1-web-banner.jpg","");
                list.add(ncle2);
                InformationObject ncle3 = new InformationObject("Intu Metrocentre","Intu Metrocentre is a mega shopping centre and entertainment complex just across the bridge in Gateshead. \n" +
                        "\n" +
                        "At 2,000,000 square feet, it's the UK's second-largest shopping centre. Not only do they boast a large number of stores where you can purchase virtually anything as per your needs, but this venue also boasts an IMAX cinema theatre.\n" +
                        "\n",
                        "","https://www.poppyshop.org.uk/media/wysiwyg/cms_pages/Metro-Centre.jpg","");
                list.add(ncle3);
                InformationObject ncle4 = new InformationObject("St. James' Park","St. James' Park is the iconic home stadium of the Premier League football club, Newcastle United. It has a seating capacity of 52,388; making it the eighth largest football stadium in England.\n" +
                        "\n" +
                        "This stadium is known to have the best crowd atmosphere in the Premier League, so we'd recommend that you take a tour of this venue or even better, go watch a game here!",
                        "","https://www.nusu.co.uk/asset/Event/30026/shutterstock_638739301.jpg?thumbnail_width=1366&thumbnail_height=640&resize_type=CropToFit","");
                list.add(ncle4);
                InformationObject ncle5 = new InformationObject("Angel of the North","Anyone who ever comes to live in North East England, most definitely has to visit this magnificent contemporary sculpture.\n" +
                        "\n" +
                        "The Angel of the North is a 66 feet tall and 177 feet wide steel sculpture of an angel located in Gateshead. The beauty and simplicity of this modern sculpture should most definitely leave you in a sense of awe.",
                        "","https://upload.wikimedia.org/wikipedia/commons/thumb/5/5a/Fly-Angel.jpg/220px-Fly-Angel.jpg","");
                list.add(ncle5);
                InformationObject ncle6 = new InformationObject("Quayside","Quayside is an area along the banks of the river Tyne. It's home to the Gateshead Millennium Bridge and other bridges too which connect Newcastle to Gateshead.\n" +
                        "\n" +
                        "This area is an amazing nightlife hotspot with multiple restaurants, bars and clubs. Apart from that, it boasts a stunning ambience and is an amazing place to take pictures at night. All in all, it's the apt place to have a fun, memorable night out with your mates.",
                        "","https://upload.wikimedia.org/wikipedia/commons/2/22/Beside_the_Quayside_-_geograph.org.uk_-_253610.jpg","");
                list.add(ncle6);
                returnInfo.setInformationObjects(list);
                break;
            case "publicTransport":
                InformationObject pt1 = new InformationObject("Tyne and Wear Metro","Quayside is an area along the banks of the river Tyne. It's home to the Gateshead Millennium Bridge and other bridges too which connect Newcastle to Gateshead.\n" +
                        "\n" +
                        "This area is an amazing nightlife hotspot with multiple restaurants, bars and clubs. Apart from that, it boasts a stunning ambience and is an amazing place to take pictures at night. All in all, it's the apt place to have a fun, memorable night out with your mates.",
                        "","https://pbs.twimg.com/profile_images/1153617467874394112/DtHawTa7_400x400.jpg","");
                list.add(pt1);
                InformationObject pt2 = new InformationObject("Bus Services","The bus services in Newcastle and Gateshead are mainly operated by Arriva, Quaylink, Stagecoach and others. They are all equipped with contactless payment facilities for tickets.\n" +
                        "\n" +
                        "There are many value bus ticket options to get around the Toon. These include the Arriva Pass, the Stagecoach Megarider Pass and the Network One Pass. You can buy them from here:\n" +
                        "\n" +
                        "               https://www.arrivabus.co.uk\n" +
                        "\n" +
                        "               https://www.stagecoachbus.com\n" +
                        "\n" +
                        "               https://networkonetickets.co.uk\n" +
                        "\n",
                        "","https://seekvectorlogo.com/wp-content/uploads/2018/04/stagecoach-uk-bus-vector-logo.png","");
                list.add(pt2);
                returnInfo.setInformationObjects(list);
                break;
            case "safety":
                InformationObject saf1 = new InformationObject("Northumbria Police","Northumbria Police is the police force responsible for policing the territorial vicinities of Northumbria and Tyne & Wear in the North-Eastern part of England.\n" +
                        "\n" +
                        "They can be contacted on:\n" +
                        "\n" +
                        "Phone: +44 1661 872555\n" +
                        "Non-Emergency Helpline: 101\n" +
                        "\n" +
                        "Emergency Helpline: 999",
                        "","https://upload.wikimedia.org/wikipedia/en/5/5d/Northumbriapolice.png","");
                list.add(saf1);
                InformationObject saf2 = new InformationObject("NHS","The National Health Service or the NHS is the publicly funded healthcare system of the United Kingdom. They provide a range of healthcare services free of cost for people residing in the UK, including students.\n" +
                        "\n" +
                        "They can be contacted on:\n" +
                        "\n" +
                        "Non-Emergency Helpline: 111\n" +
                        "\n" +
                        "Emergency Helpline: 999",
                        "","https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/NHS-Logo.svg/1200px-NHS-Logo.svg.png","");
                list.add(saf2);
                InformationObject saf3 = new InformationObject("SafeZone App","SafeZone is a free app for all students and staff that connects you directly to the university security team when you need help while you are on campus.\n" +
                        "\n" +
                        "You can get urgent help if you or someone near you feels threatened or is the victim of an assault or robbery, and call for First Aid or Help in less urgent situations. When you raise an alarm or call for help, all on-campus security team members will be alerted to your situation and location so that they can co-ordinate to help you quickly and effectively.",
                        "","https://lh3.googleusercontent.com/Z8_uih2EHrRNOE-I8cLi9a3r8F7Q7uMPsULK1FhqNc-dvfgjZsS3aFs9o5XyxVlb_O31","");
                list.add(saf3);
                returnInfo.setInformationObjects(list);
                break;
            case "maps":
                InformationObject map1 = new InformationObject("test 1","",
                        "54.9803 1.6130","","");
                   list.add(map1);
                returnInfo.setInformationObjects(list);
                break;
            case "societies":
                InformationObject soc1 = new InformationObject("NUSU","NUSU or Newcastle University Students' Union is the students' union of Newcastle University in Newcastle upon Tyne, United Kingdom. \n" +
                        "\n" +
                        "It is an organisation with the intention of representing and providing services and welfare for the students of the University of Newcastle upon Tyne.\n" +
                        "\n" +
                        "NUSU is based in the Union Building.\n\n https://www.nusu.co.uk",
                        "","https://www.nusu.co.uk/asset/Organisation/6013/NUSU%20LOGO%202.png?thumbnail_width=300&thumbnail_height=300&resize_type=ResizeWidth","");
                list.add(soc1);
                InformationObject soc2 = new InformationObject("Anglo Chinese Society","The Anglo-Chinese Society comprises of a diverse group of students with a curiosity for anything China-related. They aim to promote British China relations through social events, cultural activities and educational talks. This year they have lots of exciting events lined up: Get ready for karaoke, fireworks, tea, bubble tea, food, drinks and lots of cultural activities, as well as lots of new friends from all over the world!!!\n\n https://www.nusu.co.uk/getinvolved/societies/society/6583",
                        "","https://www.nusu.co.uk/asset/Organisation/6583/logo2.png?thumbnail_width=300&thumbnail_height=300&resize_type=ResizeWidth","");
                list.add(soc2);
                InformationObject soc3 = new InformationObject("International Society","The International Society aims to promote multiculturalism and the stunning places Northern England have to offer. They wish to create a space for intercultural dialogue amongst students who share the same advocacies of integration, inclusivity, peace and cooperation. \n" +
                        "\n" +
                        "They organise social gatherings to encourage communication and networking.\n\n https://www.nusu.co.uk/getinvolved/societies/society/6989",
                        "","https://www.nusu.co.uk/asset/Organisation/6989/logo.png?thumbnail_width=375&thumbnail_height=375&resize_type=ResizeFitAllFill","");
                list.add(soc3);
                InformationObject soc4 = new InformationObject("Chinese Students & Scholars","CSSA-NCL is committed to developing unique and attractive programs and activities locally, nationally and internationally. \n" +
                        "\n" +
                        "This enables Chinese students, scholars and other ethnic individuals who are interested in China to experience and enjoy a richer, fuller, and more successful life in the UK and beyond.\n\n https://www.nusu.co.uk/organisation/6716",
                        "","https://www.nusu.co.uk/asset/Organisation/6716/CSSA%20NCL(1).jpg?thumbnail_width=300&thumbnail_height=300&resize_type=ResizeWidth","");
                list.add(soc4);
                returnInfo.setInformationObjects(list);
                break;
            default:
                break;


        }


        return returnInfo;
    }
}
