


## How to debug

    gdb --args env LD_PRELOAD=./obj/libretrace.so RETRACE=replay RETRACE_FILE=<tracefile>.trace ls -al


## How to debug the demo recording

gdb --args env LD_PRELOAD=./obj/libretrace.so RETRACE=record RETRACE_FILE=mathieu3.trace /app/debug/python.exe /opt/demo/demo.py


Example of gdb usage
```
root@6a8ffdda4a36:/app# gdb --args env LD_PRELOAD=./obj/libretrace.so RETRACE=replay RETRACE_FILE=ls2.trace ls -al
(gdb) b __retrace_read
Function "__retrace_read" not defined.
Make breakpoint pending on future shared library load? (y or [n]) y
Breakpoint 1 (__retrace_read) pending.
(gdb) r
Starting program: /usr/bin/env LD_PRELOAD=./obj/libretrace.so RETRACE=replay RETRACE_FILE=ls2.trace ls -al
(gdb) p data
$1 = (void **) 0x7f3dd285a3e6 <buffer_clear+39>
(gdb) p length
$2 = 32767
(gdb) bt
#0  __retrace_read (data=0x7f3dd285a3e6 <buffer_clear+39>, length=32767) at src/core/retrace.c:124
#1  0x00007f3dd28696f2 in __retrace_dirent_reader (d=0x7fff84a86580) at obj/dirent.c:121
#2  0x00007f3dd2869d13 in readdir (__dirp=0x56096909e560) at obj/dirent.c:259
#3  0x0000561df8325b57 in ?? ()
#4  0x0000561df831fb5d in ?? ()
#5  0x00007f3dd26580b3 in __libc_start_main (main=0x561df831edf0, argc=2, argv=0x7fff84a86a98, init=<optimized out>, fini=<optimized out>, 
    rtld_fini=<optimized out>, stack_end=0x7fff84a86a88) at ../csu/libc-start.c:308

(gdb) p __retrace_return
```

## How to run

```
r
```

## How to continue

```
c
```


## How to add breakpoint

```
b <label>
```

## How to display backtrace (stacktrace)

```
bt
```


## Selecting a Frame
ref: https://sourceware.org/gdb/onlinedocs/gdb/Selection.html

Most commands for examining the stack and other data in your program work on whichever stack frame is selected at the moment. Here are the commands for selecting a stack frame; all of them finish by printing a brief description of the stack frame just selected.

```
frame [ frame-selection-spec ]
f [ frame-selection-spec ]
```

### how to display frame infos

Type `info variables` to list "All global and static variable names".

Type `info locals` to list "Local variables of current stack frame" (names and values), including static variables in that function.

Type `info args` to list "Arguments of the current stack frame" (names and values).

```
info frame
info args
info locals
info variables
```

## How to display value of variable

```
p <label>
```

## How to step in

```
stepi
```

## how to step out

```
finish
```