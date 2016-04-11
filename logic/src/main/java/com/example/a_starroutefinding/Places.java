package com.example.a_starroutefinding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.util.Arrays;

public class Places extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainPlaceMenu();
    }

    protected void MainPlaceMenu(){
        setContentView(R.layout.activity_places);

        Button commonPlaces = (Button) findViewById(R.id.common);
        commonPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonPlacesMenu();
            }
        });

        Button classroom_block = (Button) findViewById(R.id.classroom);
        classroom_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassroomBlockMenu();
            }
        });

        Button toilets = (Button) findViewById(R.id.toilet);
        toilets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToiletMenu();
            }
        });

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.locationStage = 0;
                finish();
            }
        });
    }

    protected void ReturnPlace(String placeName){
        if (Global.mainActivity != null) {
            if (Global.locationStage == 0) {
                Global.startLocation = placeName;
                Global.locationStage = 1;
                Intent start = new Intent(Places.this, Places.class);
                startActivity(start);
            } else {
                Global.targetLocation = placeName;
                Intent start = new Intent(Places.this, DisplayRoute.class);
                startActivity(start);
            }
        }
        finish();
    }

    protected void CommonPlacesMenu() {
        setContentView(R.layout.common_places);
        Button reception = (Button) findViewById(R.id.reception);
        reception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Reception");
            }
        });

        Button hall = (Button) findViewById(R.id.hall);
        hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Hall");
            }
        });

        Button playground = (Button) findViewById(R.id.playground);
        playground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Playground");
            }
        });

        Button commonRoom = (Button) findViewById(R.id.commonRoom);
        commonRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Common Room");
            }
        });

        Button gym = (Button) findViewById(R.id.gym);
        gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Gym");
            }
        });

        Button sports = (Button) findViewById(R.id.sports);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Sports Hall");
            }
        });

        Button pavilion = (Button) findViewById(R.id.pavilion);
        pavilion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Pavilion");
            }
        });

        Button fitness = (Button) findViewById(R.id.fitness);
        fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Fitness Room");
            }
        });

        Button swimming = (Button) findViewById(R.id.swimming);
        swimming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Swimming Pool");
            }
        });

        Button cafe = (Button) findViewById(R.id.cafe);
        cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Cafe");
            }
        });

        Button library = (Button) findViewById(R.id.library);
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Library");
            }
        });

        Button staff = (Button) findViewById(R.id.staff);
        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Staff Corridor");
            }
        });

        Button asd = (Button) findViewById(R.id.asd);
        asd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("ASD Department");
            }
        });

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPlaceMenu();
            }
        });
    }

    protected void ClassroomBlockMenu(){
        setContentView(R.layout.classroom_block);
        Button a = (Button) findViewById(R.id.a);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomNumber("A");
            }
        });

        Button b = (Button) findViewById(R.id.b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomNumber("B");
            }
        });

        Button c = (Button) findViewById(R.id.c);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomNumber("C");
            }
        });

        Button d = (Button) findViewById(R.id.d);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomNumber("D");
            }
        });

        Button e = (Button) findViewById(R.id.e);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomNumber("E");
            }
        });

        Button j = (Button) findViewById(R.id.j);
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomNumber("J");
            }
        });

        Button l = (Button) findViewById(R.id.l);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomNumber("L");
            }
        });

        Button m = (Button) findViewById(R.id.m);
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomNumber("M");
            }
        });

        Button s = (Button) findViewById(R.id.s);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomNumber("S");
            }
        });

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPlaceMenu();
            }
        });
    }

    protected void ToiletMenu(){
        setContentView(R.layout.toilets_menu);

        Button men = (Button) findViewById(R.id.men);
        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Men's Toilets");
            }
        });

        Button women = (Button) findViewById(R.id.women);
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Women's Toilets");
            }
        });

        Button disabled = (Button) findViewById(R.id.disabled);
        disabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Disabled Toilets");
            }
        });

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPlaceMenu();
            }
        });
    }

    protected void RoomNumber(String block){
        setContentView(R.layout.room_numbers);
        Integer[] rooms = {};
        switch(block){
            case "A": rooms = new Integer[] {1,2,3,4};
                break;
            case "B": rooms = new Integer[] {1,2,3,4,5,6,7,8,10,11,12,13,14,15,16};
                break;
            case "C": rooms = new Integer[] {2,3,4,5,6,7,8,9};
                break;
            case "D": rooms = new Integer[] {2,3,4,5,6,7};
                break;
            case "E": rooms = new Integer[] {1,12,13,20,21,22,23,24,30,31,32,33,34};
                break;
            case "J": rooms = new Integer[] {1,10,11,12};
                break;
            case "L": rooms = new Integer[] {1,2,3,4,5};
                break;
            case "M": rooms = new Integer[] {1,3,4,5,6};
                break;
            case "S": rooms = new Integer[] {2,11};
                break;
        }

        final String blockName = block;

        Button a1 = (Button) findViewById(R.id.a1);
        if (Arrays.asList(rooms).contains(1)){
            a1.setVisibility(View.VISIBLE);
            a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "1");
                }
            });
        } else {
            a1.setVisibility(View.GONE);
        }

        Button a2 = (Button) findViewById(R.id.a2);
        if (Arrays.asList(rooms).contains(2)){
            a2.setVisibility(View.VISIBLE);
            a2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "2");
                }
            });
        } else {
            a2.setVisibility(View.GONE);
        }

        Button a3 = (Button) findViewById(R.id.a3);
        if (Arrays.asList(rooms).contains(3)){
            a3.setVisibility(View.VISIBLE);
            a3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "3");
                }
            });
        } else {
            a3.setVisibility(View.GONE);
        }

        Button a4 = (Button) findViewById(R.id.a4);
        if (Arrays.asList(rooms).contains(4)){
            a4.setVisibility(View.VISIBLE);
            a4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "4");
                }
            });
        } else {
            a4.setVisibility(View.GONE);
        }

        Button a5 = (Button) findViewById(R.id.a5);
        if (Arrays.asList(rooms).contains(5)){
            a5.setVisibility(View.VISIBLE);
            a5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "5");
                }
            });
        } else {
            a5.setVisibility(View.GONE);
        }

        Button a6 = (Button) findViewById(R.id.a6);
        if (Arrays.asList(rooms).contains(6)){
            a6.setVisibility(View.VISIBLE);
            a6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "6");
                }
            });
        } else {
            a6.setVisibility(View.GONE);
        }

        Button a7 = (Button) findViewById(R.id.a7);
        if (Arrays.asList(rooms).contains(7)){
            a7.setVisibility(View.VISIBLE);
            a7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "7");
                }
            });
        } else {
            a7.setVisibility(View.GONE);
        }

        Button a8 = (Button) findViewById(R.id.a8);
        if (Arrays.asList(rooms).contains(8)){
            a8.setVisibility(View.VISIBLE);
            a8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "8");
                }
            });
        } else {
            a8.setVisibility(View.GONE);
        }

        Button a9 = (Button) findViewById(R.id.a9);
        if (Arrays.asList(rooms).contains(9)){
            a9.setVisibility(View.VISIBLE);
            a9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "9");
                }
            });
        } else {
            a9.setVisibility(View.GONE);
        }

        Button a10 = (Button) findViewById(R.id.a10);
        if (Arrays.asList(rooms).contains(10)){
            a10.setVisibility(View.VISIBLE);
            a10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "10");
                }
            });
        } else {
            a10.setVisibility(View.GONE);
        }

        Button a11 = (Button) findViewById(R.id.a11);
        if (Arrays.asList(rooms).contains(11)){
            a11.setVisibility(View.VISIBLE);
            a11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "11");
                }
            });
        } else {
            a11.setVisibility(View.GONE);
        }

        Button a12 = (Button) findViewById(R.id.a12);
        if (Arrays.asList(rooms).contains(12)){
            a12.setVisibility(View.VISIBLE);
            a12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "12");
                }
            });
        } else {
            a12.setVisibility(View.GONE);
        }

        Button a13 = (Button) findViewById(R.id.a13);
        if (Arrays.asList(rooms).contains(13)){
            a13.setVisibility(View.VISIBLE);
            a13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "13");
                }
            });
        } else {
            a13.setVisibility(View.GONE);
        }

        Button a14 = (Button) findViewById(R.id.a14);
        if (Arrays.asList(rooms).contains(14)){
            a14.setVisibility(View.VISIBLE);
            a14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "14");
                }
            });
        } else {
            a14.setVisibility(View.GONE);
        }

        Button a15 = (Button) findViewById(R.id.a15);
        if (Arrays.asList(rooms).contains(15)){
            a15.setVisibility(View.VISIBLE);
            a15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "15");
                }
            });
        } else {
            a15.setVisibility(View.GONE);
        }

        Button a16 = (Button) findViewById(R.id.a16);
        if (Arrays.asList(rooms).contains(16)){
            a16.setVisibility(View.VISIBLE);
            a16.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "16");
                }
            });
        } else {
            a16.setVisibility(View.GONE);
        }

        Button a17 = (Button) findViewById(R.id.a17);
        if (Arrays.asList(rooms).contains(17)){
            a17.setVisibility(View.VISIBLE);
            a17.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "17");
                }
            });
        } else {
            a17.setVisibility(View.GONE);
        }

        Button a18 = (Button) findViewById(R.id.a18);
        if (Arrays.asList(rooms).contains(18)){
            a18.setVisibility(View.VISIBLE);
            a18.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "18");
                }
            });
        } else {
            a18.setVisibility(View.GONE);
        }

        Button a20 = (Button) findViewById(R.id.a20);
        if (Arrays.asList(rooms).contains(20)){
            a20.setVisibility(View.VISIBLE);
            a20.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "20");
                }
            });
        } else {
            a20.setVisibility(View.GONE);
        }

        Button a21 = (Button) findViewById(R.id.a21);
        if (Arrays.asList(rooms).contains(21)){
            a21.setVisibility(View.VISIBLE);
            a21.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "21");
                }
            });
        } else {
            a21.setVisibility(View.GONE);
        }

        Button a22 = (Button) findViewById(R.id.a22);
        if (Arrays.asList(rooms).contains(22)){
            a22.setVisibility(View.VISIBLE);
            a22.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "22");
                }
            });
        } else {
            a22.setVisibility(View.GONE);
        }

        Button a23 = (Button) findViewById(R.id.a23);
        if (Arrays.asList(rooms).contains(23)){
            a23.setVisibility(View.VISIBLE);
            a23.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "23");
                }
            });
        } else {
            a23.setVisibility(View.GONE);
        }

        Button a24 = (Button) findViewById(R.id.a24);
        if (Arrays.asList(rooms).contains(24)){
            a24.setVisibility(View.VISIBLE);
            a24.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "24");
                }
            });
        } else {
            a24.setVisibility(View.GONE);
        }

        Button a30 = (Button) findViewById(R.id.a30);
        if (Arrays.asList(rooms).contains(30)){
            a30.setVisibility(View.VISIBLE);
            a30.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "30");
                }
            });
        } else {
            a30.setVisibility(View.GONE);
        }

        Button a31 = (Button) findViewById(R.id.a31);
        if (Arrays.asList(rooms).contains(31)){
            a31.setVisibility(View.VISIBLE);
            a31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "31");
                }
            });
        } else {
            a31.setVisibility(View.GONE);
        }

        Button a32 = (Button) findViewById(R.id.a32);
        if (Arrays.asList(rooms).contains(32)){
            a32.setVisibility(View.VISIBLE);
            a32.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "32");
                }
            });
        } else {
            a32.setVisibility(View.GONE);
        }

        Button a33 = (Button) findViewById(R.id.a33);
        if (Arrays.asList(rooms).contains(33)){
            a33.setVisibility(View.VISIBLE);
            a33.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "33");
                }
            });
        } else {
            a33.setVisibility(View.GONE);
        }

        Button a34 = (Button) findViewById(R.id.a34);
        if (Arrays.asList(rooms).contains(34)){
            a34.setVisibility(View.VISIBLE);
            a34.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReturnPlace(blockName + "34");
                }
            });
        } else {
            a34.setVisibility(View.GONE);
        }

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassroomBlockMenu();
            }
        });
    }
}
