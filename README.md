# Instruction Set Architecture Simulator

This project is an interpreter-based simulator of a custom instruction set architecture (ISA), written in any programming language. The simulator includes 64-bit registers and supports essential arithmetic, logical, and memory operations, with debugging capabilities and branching instructions. The project follows object-oriented programming principles, adheres to SOLID principles, and emphasizes clean code practices.

## Features

1. **64-bit General Purpose Registers**
   - The simulator includes **at least four 64-bit general-purpose registers** (use a data type with an 8-byte length, such as `long long` or `uint64_t`).

2. **Instruction Set**:
   - **Arithmetic Operations**: `ADD`, `SUB`, `MUL`, `DIV`
   - **Logical Operations**: `AND`, `OR`, `NOT`, `XOR`
   - **Data Transfer**: `MOV` (moves data between registers)
   - **I/O Operations**:
     - **Input**: Instruction to read data from standard input (analogous to system calls).
     - **Output**: Instruction to write data to standard output.

3. **Single-Step Debugging Support**:
   - Execute the program step-by-step, with the ability to inspect all register values and memory at specified breakpoints.
   - Console commands:
     - `NEXT` or `STEP`: Execute the next instruction.
     - `CONTINUE`: Continue execution to the next breakpoint.

4. **Memory Management**:
   - **64-bit Address Space**: The simulator has a 64-bit address space.
   - **Direct and Indirect Addressing**: Addresses can be specified directly as numbers.
   - **Memory Operations**: Use `MOV`, `LOAD`, and `STORE` instructions to access memory, which stores 1 byte per address.

5. **Branching Instructions**:
   - **Unconditional and Conditional Branching**:
     - `JMP` (unconditional jump)
     - `CMP` (comparison)
     - `JE`, `JNE` (jump if equal, jump if not equal)
     - `JGE`, `JL` (jump if greater or equal, jump if less than)

6. **Assembler and Machine Code Translation**:
   - Convert assembly instructions to guest machine code (bytecode) and store them in the guest’s memory (similar to a code/text segment).
   - Instructions are then executed directly from guest memory.
   - The simulator includes a **program counter** register, which points to the next instruction.

7. **Self-Modifying Code Support**:
   - Supports execution of self-modifying code (modifications to code that are executed both before and after modification).
   
8. **Lexical, Syntax, and Semantic Analysis**:
   - Proper analysis of source code for execution.

9. **Unit Tests**:
   - Includes a series of unit tests to demonstrate the functionality of each feature.

## Project Structure

- **Main Components**:
  - **Interpreter**: Processes assembly instructions and executes them.
  - **Memory Module**: Manages memory allocation, addressing, and access.
  - **Debugger**: Allows single-step debugging with breakpoint support.
  - **Instruction Set**: Defines supported operations and instruction handling.

- **Key Files**:
  - `interpreter.py`: The main interpreter logic.
  - `memory.py`: Manages guest memory and addressing.
  - `registers.py`: Defines and manages registers.
  - `debugger.py`: Implements debugging support.
  - `tests/`: Contains unit tests.

## Implementation Requirements

- Follow object-oriented design principles, **SOLID** principles, and clean coding practices.
- Name variables, functions, classes, and methods clearly and consistently.
- Adhere to the conventions of the chosen programming language.

## Running the Simulator

1. **Load Assembly Code**:
   - The assembly code should be loaded from a file.

2. **Execution**:
   - Run the program, enabling single-step debugging if desired.
   - Monitor the registers, memory, and breakpoints as specified.

3. **Commands**:
   - Use `NEXT` or `STEP` to step through instructions.
   - Use `CONTINUE` to proceed to the next breakpoint.

4. **Test**:
   - Run unit tests to verify each function as per project specifications.

## Example Assembly Code

An example of assembly code demonstrating a self-modifying program will be provided, showing execution before and after code modifications.

## License

This project is licensed under the MIT License.
