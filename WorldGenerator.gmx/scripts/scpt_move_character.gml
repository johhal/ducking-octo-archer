var dirr = argument[0];
var vision = argument[1];

scpt_update_vision(vision, xpos_grid, ypos_grid, -1);
scpt_update_grid_pos(xpos_grid + global.xp[dirr], ypos_grid + global.yp[dirr]);
scpt_update_vision(vision, xpos_grid, ypos_grid, 1);
