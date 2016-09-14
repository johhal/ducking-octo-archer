var dirr = argument[0];
var vision = argument[1];

//Flytta nån annan stans?!
xp[1] = 1;
xp[2] = -1;
xp[3] = 0;
xp[4] = 0;
yp[1] = 0;
yp[2] = 0;
yp[3] = -1;
yp[4] = 1;

scpt_update_vision(vision, xpos_grid, ypos_grid, -1);
scpt_update_grid_pos(xpos_grid + xp[dirr], ypos_grid + yp[dirr]);
scpt_update_vision(vision, xpos_grid, ypos_grid, 1);
