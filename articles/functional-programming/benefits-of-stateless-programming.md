


Mutability is bad. You should avoid state.



## Avoid Mutability at all cost

Avoid mutability at all cost unless there is a clear and practical reason to do. Even then consider avoiding it.


## Isolate state

When state is introduced it should be minimal and isolated in a way which makes it easier to keep it consistent. For example using transctional memory like Atoms.


## Why is mutability such a source problems ?

Because it violates the following

### Referential Transparency

#### Reasoning about the code

#### Tracking bugs

### Thread Safety

### Testing
