var vision = argument[0];
var pos_x = argument[1];
var pos_y = argument[2];
var modifier = argument[3];

var i = 0;
var j = 0;
var k = 0;

if(vision == 0) return 0;

for(j = -vision; j <= vision; j++)  
{
    for(k = -vision; k <= vision; k++){
        if(scpt_inside_world(pos_x + j, pos_y + k))
        {
            global.world_map[# pos_x + j, pos_y + k].units_with_vision += modifier;
            if(global.world_map[# pos_x + j, pos_y + k].units_with_vision != 0)
            {
                global.world_map[# pos_x + j, pos_y + k].depth = 10;
                global.world_map[# pos_x + j, pos_y + k].image_index = 1;
            }
            else
            {    
                global.world_map[# pos_x + j, pos_y + k].depth = 0;
                global.world_map[# pos_x + j, pos_y + k].image_index = 2;
            }
        }
    }
}
