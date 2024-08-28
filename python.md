


#### Step 2: Create a Virtual Environment
Create a virtual environment to manage the dependencies for the project:

```bash
python3.12 -m venv venv
```

#### Step 3: Activate the Virtual Environment
Activate the virtual environment:

```bash
source venv/bin/activate
```

#### Step 4: Install Dependencies
Install the required packages:

```bash
pip install -r requirements.txt
```

#### Running the Application
Explain how to run the application. For instance:

```bash
pip install -r requirements.txt
```

#### Running Tests
Describe how to run the automated tests for this system:

```bash
python -m unittest discover
```


## Start a simple server

    python -m SimpleHTTPServer


## How to retrieve source code of functions

ref: https://opensource.com/article/18/5/how-retrieve-source-code-python-functions

```
import inspect
source_DF = inspect.getsource(pandas.DataFrame)
print(type(source_DF))
print(len(source_DF))
print(source_DF[:200])
```

## How to reload module

```
import importlib
importlib.reload(retrace)
```

## Misc

pip3 install ast2json

replay traceexample.yaml RETRACE_PRINT_LAST_STEP_COUNTER=true
replay traceexample.yaml RETRACE_YIELD_AT=206

```
import retrace
import inspect
retrace.stackframes(frame).type
len(retrace.stackframes(frame))
retrace.stackframes(frame)[0]
map(lambda x: x*x,retrace.stackframes(frame))
type(retrace.stackframes(frame)[0])
retrace.stackframes(frame)[0].get('name')
list(map(lambda frame: {key: frame[key] for key in frame.keys() & {'name', 'src'}}, (retrace.stackframes(frame))))
stackframes_source = inspect.getsource(retrace.stackframes)
dir(frame)
frame.value_stack
import dis
dis.get_instructions(retrace.stackframes)
list(dis.get_instructions(frame.f_code))
dir(frame)
list(frame.f_code)
list(frame.value_stack(0))
frame.value_stack_size()
frame.value_stack_counter(0)
frame.bytecode_step_counter(0)
len(list(dis.get_instructions(frame.f_code)))
frame.bytecode_step_counter(20)
a=42
dir(a)
creation_counter(a)
list(dis.get_instructions(frame.f_code))
list(dis.get_instructions(retrace.stackframes))
```

