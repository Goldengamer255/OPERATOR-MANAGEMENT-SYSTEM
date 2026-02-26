# Operator Management System 🗺️

A terminal-based Operator Management System built in Java to manage tourism operators across New Zealand, handling activities, reviews, and operator data at scale using object-oriented programming principles.

---

## 🚀 Features

- **100+ tourism operator management** across New Zealand via CLI
- **13+ commands** for creating operators, managing activities, and processing reviews
- **Multi-type review system** supporting public, private, and expert reviews
- **Review handling logic** with endorsement, resolution, and image upload support
- **Scalable architecture** projected to support 10,000+ reviews annually

---

## 🛠️ Tech Stack

- Java
- Object-Oriented Programming (OOP)
- Command Line Interface (CLI)

---

## 📂 Project Structure

```
├── src/
│   ├── Main.java                  # Entry point and CLI handler
│   ├── Operator.java              # Operator model
│   ├── Activity.java              # Activity model
│   ├── Review.java                # Base review class
│   ├── PublicReview.java          # Public review type
│   ├── PrivateReview.java         # Private review type
│   ├── ExpertReview.java          # Expert review type
│   └── ManagementSystem.java      # Core system logic
└── README.md
```

---

## 🏃 How to Run

1. Clone the repository:
```bash
git clone https://github.com/YOURUSERNAME/operator-management-system.git
```

2. Compile the project:
```bash
javac src/*.java
```

3. Run the system:
```bash
java Main
```

4. Use any of the 13+ available CLI commands to interact with the system.

---

## 💻 Available Commands

| Command | Description |
|---|---|
| `create-operator` | Add a new tourism operator |
| `add-activity` | Attach an activity to an operator |
| `add-public-review` | Submit a public review |
| `add-private-review` | Submit a private review |
| `add-expert-review` | Submit an expert review |
| `endorse-review` | Endorse an existing review |
| `resolve-review` | Mark a review as resolved |
| `upload-image` | Attach an image to a review |
| `list-operators` | Display all operators |
| `...` | 13+ commands total |

---

## 📊 Highlights

| Feature | Detail |
|---|---|
| Operators Supported | 100+ |
| CLI Commands | 13+ |
| Review Types | Public, Private, Expert |
| Projected Annual Reviews | 10,000+ |
| Architecture | OOP with scalable design |

---

## 🎓 What I Learned

- Designing scalable OOP systems with inheritance and polymorphism
- Building robust CLI interfaces with command parsing
- Implementing multi-type review systems with endorsement and resolution logic
- Structuring Java projects for maintainability and future scalability

---

## 📬 Contact

**Tanush Panuganti**
- GitHub: [Goldengamer255](https://github.com/Goldengamer255)
- LinkedIn: [tanush-panuganti](https://linkedin.com/in/tanush-panuganti)
- Email: tanush.panu@gmail.com
