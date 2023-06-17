export const ADMIN = 0;
export const PARTNER = 1;
export const CUSTOMER = 2;
export const CAMPAIGN_STATUS = {
  NOT_START: 0,
  IN_PROGRESS: 1,
  END: 2,
};

export const WordScrambleQuestion = [
  {
    id: 0,
    scrambledWord: "A S O L N M",
    hint: "This is the name of a fish that live in cold water",
    correct: "SALMON",
  },
  {
    id: 1,
    scrambledWord: "N T M A V E I",
    hint: "This is a name of a country in South Eat Asia",
    correct: "VIETNAM",
  },
  {
    id: 2,
    scrambledWord: "U N I A D R",
    hint: "This is a name of a tropical fruit that is often considered king of fruit",
    correct: "DURIAN",
  },
];

export const QuizGameQuestions = [
  {
    quizId: 0,
    question: "Việt Nam gắn liền với lục địa và đại dương nào sau đây?",
    correct: "D",
    correctAnswer: "Á-Âu và Thái Bình Dương.",
    answers: [
      {
        value: "A",
        content: "Á-Âu và Bắc Băng Dương.",
      },
      {
        value: "B",
        content: "Á- Âu và Đại Tây Dương.",
      },
      {
        value: "C",
        content: "Á-Âu và Ấn Độ Dương.",
      },
      {
        value: "D",
        content: "Á-Âu và Thái Bình Dương.",
      },
    ],
  },
  {
    quizId: 1,
    question: "Nước ta nằm trong múi giờ thứ mấy?",
    correct: "C",
    correctAnswer: "7",
    answers: [
      {
        value: "A",
        content: "5",
      },
      {
        value: "B",
        content: "6",
      },
      {
        value: "C",
        content: "7",
      },
      {
        value: "D",
        content: "8",
      },
    ],
  },
  {
    quizId: 2,
    question: "Trên đất liền, nước ta không có chung biên giới với nước nào?",
    correct: "B",
    correctAnswer: "Thái Lan",
    answers: [
      {
        value: "A",
        content: "Lào",
      },
      {
        value: "B",
        content: "Thái Lan",
      },
      {
        value: "C",
        content: "Trung Quốc",
      },
      {
        value: "D",
        content: "Campuchia",
      },
    ],
  },
];
